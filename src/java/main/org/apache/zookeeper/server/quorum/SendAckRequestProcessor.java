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

import java.io.Flushable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.zookeeper.ZooDefs.OpCode;
import org.apache.zookeeper.server.Request;
import org.apache.zookeeper.server.RequestProcessor;

public class SendAckRequestProcessor implements RequestProcessor, Flushable, RequestFlushable {
    private static final Logger LOG = LoggerFactory.getLogger(SendAckRequestProcessor.class);
    
    Learner learner;

	private FollowerZooKeeperServer fzks;

    SendAckRequestProcessor(FollowerZooKeeperServer fzks, Learner peer) {
        this.fzks = fzks;
		this.learner = peer;
    }

    public void processRequest(Request si) {
        if(si.type != OpCode.sync){
            QuorumPacket qp = new QuorumPacket(Leader.ACK, si.hdr.getZxid(), null,
                null);
            try {
            	if(LOG.isDebugEnabled()){
            		LOG.debug("Getting follower for request:" + si);
            	}
                Follower follower = fzks.getFollower(si);
                follower.writePacket(qp, false);
            } catch (IOException e) {
                LOG.warn("Closing connection to leader, exception during packet send", e);
                try {
                    if (!fzks.getFollower(si).sock.isClosed()) {
                    	fzks.getFollower(si).sock.close();
                    }
                } catch (IOException e1) {
                    // Nothing to do, we are shutting things down, so an exception here is irrelevant
                    LOG.debug("Ignoring error closing the connection", e1);
                }
            }
        }
    }
    
    public void flush(Request si) {
    	//TODO: ZKP: Commented out as it's an optimization for read heavy workloads anyway
        try {
		    Follower follower = fzks.getFollower(si);
		    follower.writePacket(null, true);
//            learner.writePacket(null, true);
        } catch(IOException e) {
            LOG.warn("Closing connection to leader, exception during packet send", e);
            try {
                if (!learner.sock.isClosed()) {
                    learner.sock.close();
                }
            } catch (IOException e1) {
                    // Nothing to do, we are shutting things down, so an exception here is irrelevant
                    LOG.debug("Ignoring error closing the connection", e1);
            }
        }
    }

    public void shutdown() {
        // Nothing needed
    }

	@Override
	public void flush() throws IOException {
	}

}
