package org.apache.zookeeper.server.quorum;

import org.apache.zookeeper.server.quorum.QuorumPeer.ServerState;

public class MockMultiLeaderElection implements Election {

	private QuorumPeer quorumPeer;
	private int leaderId;

	public MockMultiLeaderElection(QuorumPeer quorumPeer, int leaderId) {
		this.quorumPeer = quorumPeer;
		this.leaderId = leaderId;
	}

	@Override
	public Vote lookForLeader() throws InterruptedException {
		if(quorumPeer.getMyid() == leaderId){
			quorumPeer.setPeerState(ServerState.LEADING);
		}else{
			quorumPeer.setPeerState(ServerState.FOLLOWING);
		}
		return new Vote(leaderId,0,0,0);	
	}

	@Override
	public void shutdown() {
	}

}
