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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.apache.zookeeper.KeeperException.SessionExpiredException;
import org.apache.zookeeper.KeeperException.SessionMovedException;
import org.apache.zookeeper.jmx.MBeanRegistry;
import org.apache.zookeeper.server.DataTreeBean;
import org.apache.zookeeper.server.ServerCnxn;
import org.apache.zookeeper.server.SessionTracker;
import org.apache.zookeeper.server.ZKDatabase;
import org.apache.zookeeper.server.ZooKeeperServerBean;
import org.apache.zookeeper.server.persistence.FileTxnSnapLog;

/**
 * Parent class for all ZooKeeperServers for Learners 
 */
public abstract class LearnerZooKeeperServer extends QuorumZooKeeperServer {    
    private static final long id = System.currentTimeMillis();

	public LearnerZooKeeperServer(FileTxnSnapLog logFactory, int tickTime,
            int minSessionTimeout, int maxSessionTimeout,
            DataTreeBuilder treeBuilder, ZKDatabase zkDb, QuorumPeer self)
        throws IOException
    {
        super(logFactory, tickTime, minSessionTimeout, maxSessionTimeout,
                treeBuilder, zkDb, self);
        
//        id = System.currentTimeMillis();
    }

    /**
     * Abstract method to return the learner associated with this server.
     * Since the Learner may change under our feet (when QuorumPeer reassigns
     * it) we can't simply take a reference here. Instead, we need the 
     * subclasses to implement this.     
     */
    abstract public Learner getLearner();        
    
    /**
     * Returns the current state of the session tracker. This is only currently
     * used by a Learner to build a ping response packet.
     * 
     */
    protected HashMap<Long, Integer> getTouchSnapshot() {
        if (sessionTracker != null) {
            return ((LearnerSessionTracker) sessionTracker).snapshot();
        }
        return new HashMap<Long, Integer>();
    }
    
    /**
     * Returns the id of the associated QuorumPeer, which will do for a unique
     * id of this server. 
     */
    @Override
    public long getServerId() {
    	//ZKP: Somewhat of a hack, for now
    	return this.id;
//        return self.getId();
    }    
    
    @Override
    public void createSessionTracker() {
        sessionTracker = new LearnerSessionTracker(this, getZKDatabase().getSessionWithTimeOuts(),
        		getServerId());
//                self.getId());
    }
    
    @Override
    protected void startSessionTracker() {}
    
    @Override
    protected void revalidateSession(ServerCnxn cnxn, long sessionId,
            int sessionTimeout) throws IOException {
        getLearner().validateSession(cnxn, sessionId, sessionTimeout);
    }
    
    @Override
    protected void registerJMX() {
        // register with JMX
        try {
            jmxDataTreeBean = new DataTreeBean(getZKDatabase().getDataTree());
            MBeanRegistry.getInstance().register(jmxDataTreeBean, jmxServerBean);
        } catch (Exception e) {
            LOG.warn("Failed to register with JMX", e);
            jmxDataTreeBean = null;
        }
    }

    public void registerJMX(ZooKeeperServerBean serverBean,
            LocalPeerBean localPeerBean)
    {
        // register with JMX
        if (self.jmxLeaderElectionBean != null) {
            try {
                MBeanRegistry.getInstance().unregister(self.jmxLeaderElectionBean);
            } catch (Exception e) {
                LOG.warn("Failed to register with JMX", e);
            }
            self.jmxLeaderElectionBean = null;
        }

        try {
            jmxServerBean = serverBean;
            MBeanRegistry.getInstance().register(serverBean, localPeerBean);
        } catch (Exception e) {
            LOG.warn("Failed to register with JMX", e);
            jmxServerBean = null;
        }
    }

    @Override
    protected void unregisterJMX() {
        // unregister from JMX
        try {
            if (jmxDataTreeBean != null) {
                MBeanRegistry.getInstance().unregister(jmxDataTreeBean);
            }
        } catch (Exception e) {
            LOG.warn("Failed to unregister with JMX", e);
        }
        jmxDataTreeBean = null;
    }

    protected void unregisterJMX(Learner peer) {
        // unregister from JMX
        try {
            if (jmxServerBean != null) {
                MBeanRegistry.getInstance().unregister(jmxServerBean);
            }
        } catch (Exception e) {
            LOG.warn("Failed to unregister with JMX", e);
        }
        jmxServerBean = null;
    }
}
