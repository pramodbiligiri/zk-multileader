/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.zookeeper.server.quorum;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.zookeeper.ZooDefs.OpCode;
import org.apache.zookeeper.server.RequestProcessor;
import org.apache.zookeeper.server.Request;
import org.apache.zookeeper.server.ZooTrace;
import org.apache.zookeeper.txn.CreateSessionTxn;

/**
 * This RequestProcessor forwards any requests that modify the state of the
 * system to the Leader.
 */
public class FollowerRequestProcessor extends Thread implements
        RequestProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(FollowerRequestProcessor.class);

    FollowerZooKeeperServer zks;

    RequestProcessor nextProcessor;

    LinkedBlockingQueue<Request> queuedRequests = new LinkedBlockingQueue<Request>();

    boolean finished = false;

    public FollowerRequestProcessor(FollowerZooKeeperServer zks,
            RequestProcessor nextProcessor) {
        super("FollowerRequestProcessor:" + zks.getServerId());
        this.zks = zks;
        this.nextProcessor = nextProcessor;
    }

    @Override
    public void run() {
        try {
            while (!finished) {
                Request request = queuedRequests.take();
                if (LOG.isTraceEnabled()) {
                    ZooTrace.logRequest(LOG, ZooTrace.CLIENT_REQUEST_TRACE_MASK,
                            'F', request, "");
                }
                if (request == Request.requestOfDeath) {
                    break;
                }
                // We want to queue the request to be processed before we submit
                // the request to the leader so that we are ready to receive
                // the response
                nextProcessor.processRequest(request);
                
                // We now ship the request to the leader. As with all
                // other quorum operations, sync also follows this code
                // path, but different from others, we need to keep track
                // of the sync operations this follower has pending, so we
                // add it to pendingSyncs.
                switch (request.type) {
                case OpCode.sync:
                    zks.pendingSyncs.add(request);
                    zks.getFollower().request(request);
                    break;
                case OpCode.create:
                 	//TODO: ZKP: Find the corresponding follower-leader connection based
                	//on the request path, and send
                    zks.getFollower(request).request(request);
                    break;
                case OpCode.createSession:
                	//TODO: ZKP: Find the corresponding follower-leader connection based
                	//on the request path, and send
                	request.request.rewind();
                	int to = request.request.getInt();
                	byte[] path = new byte[request.request.remaining()];
                	request.request.get(path, 0, path.length);
                	request.txn = new CreateSessionTxn(to, path);
                	request.request.rewind();
                	//Note: Fall through!
                    
                case OpCode.delete:
                case OpCode.setData:
                case OpCode.setACL:
                case OpCode.closeSession:
                case OpCode.multi:
                	
                	Follower follower = zks.getFollower(request);
                	
//                    Follower follower = zks.getFollower();
                    if(LOG.isDebugEnabled()){
                    	LOG.debug("Got follower: " + follower +" for request:" + request);
                    }
                    
					follower.request(request);
                    break;
                }
            }
        } catch (Exception e) {
            LOG.error("Unexpected exception causing exit", e);
        }
        LOG.info("FollowerRequestProcessor exited loop!");
    }

    public void processRequest(Request request) {
        if (!finished) {
            queuedRequests.add(request);
        }
    }

    public void shutdown() {
        LOG.info("Shutting down");
        finished = true;
        queuedRequests.clear();
        queuedRequests.add(Request.requestOfDeath);
        nextProcessor.shutdown();
    }

}
