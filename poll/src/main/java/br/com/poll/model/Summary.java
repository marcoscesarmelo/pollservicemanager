package br.com.poll.model;

import java.io.Serializable;
import java.util.List;

public class Summary implements Serializable {

	private static final long serialVersionUID = 2715853774776718077L;
	
	private Poll poll;
	private List<Alternative> alternatives;
	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	public List<Alternative> getAlternatives() {
		return alternatives;
	}
	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}

}
