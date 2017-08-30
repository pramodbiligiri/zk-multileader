package org.apache.zookeeper.server.quorum;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.MDC;
import org.apache.zookeeper.server.quorum.QuorumPeer.LearnerType;
import org.apache.zookeeper.server.quorum.QuorumPeer.QuorumServer;
import org.apache.zookeeper.server.quorum.flexible.QuorumVerifier;

public class QuorumPeerConfigMultiQuorum extends QuorumPeerConfig {
	
	private QuorumPeerConfig config;
	private int quorumId;

	public QuorumPeerConfigMultiQuorum(QuorumPeerConfig config, int quorumId){
		this.config = config;
		this.quorumId = quorumId;
	}

	@Override
	public InetSocketAddress getClientPortAddress() {
		return config.getClientPortAddress();
	}
	
	@Override
	public String getDataDir() {
		return config.getDataDir() + "." + quorumId;
	}
	
	@Override
	public String getDataLogDir() {
		return config.getDataLogDir() + "." + quorumId;
	}
	
	@Override
	public int getElectionAlg() {
		return config.getElectionAlg();
	}
	
	@Override
	public int getElectionPort() {
		return config.getElectionPort();
	}
	
	@Override
	public int getInitLimit() {
		return config.getInitLimit();
	}
	
	@Override
	public int getMaxClientCnxns() {
		return config.getMaxClientCnxns();
	}
	
	@Override
	public int getMaxSessionTimeout() {
		// TODO Auto-generated method stub
		return config.getMaxSessionTimeout();
	}
	
	@Override
	public int getMinSessionTimeout() {
		return config.getMinSessionTimeout();
	}
	
	@Override
	public LearnerType getPeerType() {
		return config.getPeerType();
	}
	
	@Override
	public int getPurgeInterval() {
		return config.getPurgeInterval();
	}
	
	@Override
	public Boolean getQuorumListenOnAllIPs() {
		return config.getQuorumListenOnAllIPs();
	}
	
	@Override
	public QuorumVerifier getQuorumVerifier() {
		return config.getQuorumVerifier();
	}
	
	@Override
	public long getServerId() {
		return config.getServerId();
	}
	
	@Override
	public Map<Long, QuorumServer> getServers() {
		return config.getServers();
	}
	
	@Override
	public int getSnapRetainCount() {
		return config.getSnapRetainCount();
	}
	
	@Override
	public boolean getSyncEnabled() {
		return config.getSyncEnabled();
	}
	
	@Override
	public int getSyncLimit() {
		return config.getSyncLimit();
	}
	
	@Override
	public int getTickTime() {
		return config.getTickTime();
	}
	
	@Override
	public boolean isDistributed() {
		return config.isDistributed();
	}
	
	@Override
	public void parse(String path) throws ConfigException {
		config.parse(path);
	}
	
	@Override
	public void parseProperties(Properties zkProp) throws IOException,
			ConfigException {
		config.parseProperties(zkProp);
		MDC.put("quorumId", quorumId);
	}
	
	@Override
	public boolean isDiskWritesDisabled(){
		return config.isDiskWritesDisabled();
	}
	
}
