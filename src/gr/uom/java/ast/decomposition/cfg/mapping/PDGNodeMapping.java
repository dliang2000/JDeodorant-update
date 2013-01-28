package gr.uom.java.ast.decomposition.cfg.mapping;

import java.util.List;

import gr.uom.java.ast.decomposition.ASTNodeDifference;
import gr.uom.java.ast.decomposition.Difference;
import gr.uom.java.ast.decomposition.cfg.AbstractVariable;
import gr.uom.java.ast.decomposition.cfg.CompositeVariable;
import gr.uom.java.ast.decomposition.cfg.PDGNode;

public class PDGNodeMapping {
	private PDGNode nodeG1;
	private PDGNode nodeG2;
	private ASTNodeDifference nodeDifference;
	private volatile int hashCode = 0;
	
	public PDGNodeMapping(PDGNode nodeG1, PDGNode nodeG2, ASTNodeDifference nodeDifference) {
		this.nodeG1 = nodeG1;
		this.nodeG2 = nodeG2;
		this.nodeDifference = nodeDifference;
	}
	
	public PDGNode getNodeG1() {
		return nodeG1;
	}

	public PDGNode getNodeG2() {
		return nodeG2;
	}

	public boolean matchingVariableDifference(AbstractVariable variable1, AbstractVariable variable2) {
		if(variable1.getClass() == variable2.getClass()) {
			String rightPartVariable1 = null;
			String rightPartVariable2 = null;
			if(variable1 instanceof CompositeVariable) {
				CompositeVariable comp1 = (CompositeVariable)variable1;
				CompositeVariable comp2 = (CompositeVariable)variable2;
				rightPartVariable1 = comp1.getRightPart().toString();
				rightPartVariable2 = comp2.getRightPart().toString();
			}
			boolean equalRightPart = false;
			if(rightPartVariable1 != null && rightPartVariable2 != null) {
				equalRightPart = rightPartVariable1.equals(rightPartVariable2);
			}
			else {
				equalRightPart = true;
			}
			List<Difference> leafDifferences = nodeDifference.getLeafDifferences();
			for(Difference difference : leafDifferences) {
				if(equalRightPart && difference.getFirstValue().equals(variable1.getVariableName()) &&
						difference.getSecondValue().equals(variable2.getVariableName())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o instanceof PDGNodeMapping) {
			PDGNodeMapping mapping = (PDGNodeMapping)o;
			return this.nodeG1.equals(mapping.nodeG1) &&
					this.nodeG2.equals(mapping.nodeG2);
		}
		return false;
	}

	public int hashCode() {
		if(hashCode == 0) {
			int result = 17;
			result = 37*result + nodeG1.hashCode();
			result = 37*result + nodeG2.hashCode();
			hashCode = result;
		}
		return hashCode;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(nodeG1);
		sb.append(nodeG2);
		sb.append(nodeDifference.toString());
		return sb.toString();
	}
}
