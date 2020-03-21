package org.javers.core.cases

import org.javers.core.Javers
import org.javers.core.JaversBuilder
import org.javers.core.commit.Commit

import spock.lang.Specification

class Case948GenericCommitTupleLikeObject extends Specification {
	class Pair<L, R> {
		L left;
		R right;

		public Pair(L left, R right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public String toString() {
			return "Pair [left=" + left + ", right=" + right + "]";
		}
	}

	def "should tracking work when committing generic-tuple-like object"(){
		given:
		Javers javers = JaversBuilder.javers().build();
		//Pair<Long, String> obj = org.apache.commons.lang3.tuple.MutablePair.of(1L, "foo");
		Pair<Long, String> obj = new Pair<>(1L, "foo");
		//    Map<Long,String> obj = new HashMap();
		//    obj.put(1L+"", "foo")

		when:
		javers.commit("jay", obj);

		//obj.setValue("bar");
		obj.right = "bar";
		//    obj.put(1L, "bar")

		Commit commit = javers.commit("jay", obj);

		then:
		commit.getChanges().size() == 1
	}
}
