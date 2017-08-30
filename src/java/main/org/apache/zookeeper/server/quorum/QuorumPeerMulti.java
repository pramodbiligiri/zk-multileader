package org.apache.zookeeper.server.quorum;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.ZooDefs.OpCode;
import org.apache.zookeeper.server.Request;
import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ZKDatabase;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.apache.zookeeper.server.persistence.FileTxnSnapLog;
import org.apache.zookeeper.server.quorum.QuorumPeer.LearnerType;
import org.apache.zookeeper.server.quorum.QuorumPeer.QuorumServer;
import org.apache.zookeeper.server.quorum.flexible.QuorumVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.DocFlavor.URL;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class QuorumPeerMulti extends QuorumPeer {
	private static final Logger LOG = LoggerFactory.getLogger(QuorumPeer.class);
	private List<QuorumPeer> quorumPeersList = new ArrayList<QuorumPeer>();
	private CompiledScript script;
	private ScriptEngine engine;

	public void add(QuorumPeer quorumPeer) {
		quorumPeersList.add(quorumPeer);
	}
	
    public LearnerType getLearnerType() {
    	throw new NotImplementedException();    
    }
    
    public void setLearnerType(LearnerType p) {
    	throw new NotImplementedException();    
    }

    public int getQuorumSize(){
    	return quorumPeersList.get(0).getQuorumSize();	
    }

    public long getId() {
    	throw new NotImplementedException();    
    }

    public synchronized Vote getCurrentVote(){
    	throw new NotImplementedException();    
    }
       
    public synchronized void setCurrentVote(Vote v){
    	throw new NotImplementedException();    }
    
    synchronized Vote getBCVote() {
    	throw new NotImplementedException();    }

    synchronized void setBCVote(Vote v) {
    	throw new NotImplementedException();    }
    
    public synchronized void setPeerState(ServerState newState){
    	throw new NotImplementedException();
    }

    public synchronized ServerState getPeerState(){
    	throw new NotImplementedException();
    }

    public InetSocketAddress getQuorumAddress(){
    	throw new NotImplementedException();
    }
    
    QuorumStats quorumStats() {
    	throw new NotImplementedException();
    }
    
    @Override
    public synchronized void start() {
    	throw new NotImplementedException();
    }

    private void loadDataBase() {
    	throw new NotImplementedException();
	}

    synchronized public void stopLeaderElection() {
    	throw new NotImplementedException();
    }
    
    synchronized public void startLeaderElection() {
    	throw new NotImplementedException();
    }
    
    protected static int countParticipants(Map<Long,QuorumServer> peers) {
    	throw new NotImplementedException();
    }
    
    public long getLastLoggedZxid() {
    	throw new NotImplementedException();
    }
    
    protected Follower makeFollower(FileTxnSnapLog logFactory) throws IOException {
    	throw new NotImplementedException();
    }
     
    protected Leader makeLeader(FileTxnSnapLog logFactory) throws IOException {
    	throw new NotImplementedException();
    }
    
    protected Observer makeObserver(FileTxnSnapLog logFactory) throws IOException {
    	throw new NotImplementedException();
    }

    protected Election createElectionAlgorithm(int electionAlgorithm){
    	throw new NotImplementedException();
    }

    protected Election makeLEStrategy(){
    	throw new NotImplementedException();
    }

    synchronized protected void setLeader(Leader newLeader){
    	throw new NotImplementedException();
    }

    synchronized protected void setFollower(Follower newFollower){
    	throw new NotImplementedException();
    }
    
    synchronized protected void setObserver(Observer newObserver){
    	throw new NotImplementedException();
    }

    synchronized public ZooKeeperServer getActiveServer(){
    	throw new NotImplementedException();
    }

    @Override
    public void run() {
    	throw new NotImplementedException();
    }

    public void shutdown() {
    	throw new NotImplementedException();
    }

    public Map<Long,QuorumPeer.QuorumServer> getView() { 	
    	throw new NotImplementedException();
    }
    
    public Map<Long,QuorumPeer.QuorumServer> getVotingView() {
    	throw new NotImplementedException();
    }
    
    public Map<Long,QuorumPeer.QuorumServer> getObservingView() {
    	throw new NotImplementedException();
    }

    public boolean viewContains(Long sid) {
    	throw new NotImplementedException();
    }
    
    public String[] getQuorumPeers() {
    	throw new NotImplementedException();
    }

    public String getServerState() {
    	throw new NotImplementedException();
    }

    public long getMyid() {
    	throw new NotImplementedException();
    }

    public void setMyid(long myid) {
    	throw new NotImplementedException();
    }

    public int getTickTime() {
    	throw new NotImplementedException();
    }

    public void setTickTime(int tickTime) {
    	throw new NotImplementedException();
    }

    public int getMaxClientCnxnsPerHost() {
    	throw new NotImplementedException();
    }
    
    public int getMinSessionTimeout() {
    	throw new NotImplementedException();
    }

    public void setMinSessionTimeout(int min) {
    	throw new NotImplementedException();
    }

    public int getMaxSessionTimeout() {
    	throw new NotImplementedException();
    }

    public void setMaxSessionTimeout(int max) {
    	throw new NotImplementedException();
    }

    public int getInitLimit() {
    	throw new NotImplementedException();
    }

    public void setInitLimit(int initLimit) {
    	throw new NotImplementedException();
    }

    public int getTick() {
    	throw new NotImplementedException();
    }
    
    public QuorumVerifier getQuorumVerifier(){
    	throw new NotImplementedException();
    }
    
    public void setQuorumVerifier(QuorumVerifier quorumConfig){
    	throw new NotImplementedException();
    }
    
    public Election getElectionAlg(){
    	throw new NotImplementedException();
    }
        
    public int getSyncLimit() {
    	throw new NotImplementedException();
    }

    public void setSyncLimit(int syncLimit) {
    	throw new NotImplementedException();
    }

    public boolean getSyncEnabled() {
    	throw new NotImplementedException();
    }
    
    public void setSyncEnabled(boolean syncEnabled) {
    	throw new NotImplementedException();
    }

    public int getElectionType() {
    	throw new NotImplementedException();
    }

    public void setElectionType(int electionType) {
    	throw new NotImplementedException();
    }

    public boolean getQuorumListenOnAllIPs() {
    	throw new NotImplementedException();
    }

    public void setQuorumListenOnAllIPs(boolean quorumListenOnAllIPs) {
    	throw new NotImplementedException();
    }

    public ServerCnxnFactory getCnxnFactory() {
    	throw new NotImplementedException();
    }

    public void setCnxnFactory(ServerCnxnFactory cnxnFactory) {
    	throw new NotImplementedException();
    }

    public void setQuorumPeers(Map<Long,QuorumServer> quorumPeers) {
    	throw new NotImplementedException();
    }

    public int getClientPort() {
    	throw new NotImplementedException();
    }

    public void setClientPortAddress(InetSocketAddress addr) {
    	throw new NotImplementedException();
    }
 
    public void setTxnFactory(FileTxnSnapLog factory) {
    	throw new NotImplementedException();
    }
    
    public FileTxnSnapLog getTxnFactory() {
    	throw new NotImplementedException();
    }

    public void setZKDatabase(ZKDatabase database) {
    	throw new NotImplementedException();
    }

    public void setRunning(boolean running) {
    	throw new NotImplementedException();
    }

    public boolean isRunning() {
    	throw new NotImplementedException();
    }

    public QuorumCnxManager getQuorumCnxManager() {
    	throw new NotImplementedException();
    }

    private long readLongFromFile(String name) throws IOException {
    	throw new NotImplementedException();
    }

    private void writeLongToFile(String name, long value) throws IOException {
    	throw new NotImplementedException();
    }

    public long getCurrentEpoch() throws IOException {
    	throw new NotImplementedException();
	}
	
	public long getAcceptedEpoch() throws IOException {
		throw new NotImplementedException();
	}
	
	public void setCurrentEpoch(long e) throws IOException {
		throw new NotImplementedException();
	}
	
	public void setAcceptedEpoch(long e) throws IOException {
		throw new NotImplementedException();
	}

    protected void updateElectionVote(long newEpoch) {
    	throw new NotImplementedException();
    }

	public void setQuorumId(int quorumId) {
		throw new NotImplementedException();
	}
	
	public int getQuorumId() {
		throw new NotImplementedException();
	}

	public void setStartClientConnection(boolean startClientConnection) {
		throw new NotImplementedException();
	}

	public void setQuorumPeerMulti(QuorumPeerMulti quorumPeerMulti) {
		throw new NotImplementedException();
	}
	
	public void initJs(){
		try {
			ScriptEngineManager routingMgr = new ScriptEngineManager();
			engine = routingMgr.getEngineByName("javascript");
			Compilable compilingEngine = (Compilable)engine;
			script = compilingEngine.compile(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("routingScript.js")));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	public QuorumPeer getQuorumFor(Request request) {
		String rootPath = request.getRootPath();
		
		if (request.type == OpCode.create || request.type == OpCode.createSession) {
			return "app1".equals(request.getRootPath()) ? quorumPeersList.get(1) : 
				quorumPeersList.get(0);
//			Bindings bindings = engine.createBindings();
//			bindings.put("rootPath", rootPath);
//			 
//			try {
//				script.eval(bindings);
//			} catch (ScriptException e) {
//				e.printStackTrace();
//			}
//			 
//            Double nodeIndexDbl = (Double) bindings.get("nodeIndex");
//            int nodeIndex = Integer.valueOf(nodeIndexDbl.intValue());
////            LOG.debug("Got nodeIndex : " + nodeIndex);
//            return quorumPeersList.get(nodeIndex);
		}
		return quorumPeersList.get(0);
	}
	
	public Follower getFollower(){
		return quorumPeersList.get(0).getFollower();
	}
}
