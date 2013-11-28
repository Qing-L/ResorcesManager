package Resorce;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

class TreeRenderer extends DefaultTreeCellRenderer 
{
	private static final long serialVersionUID = 1L;

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		MyNode node = (MyNode) value;
		Icon icon = node.getIcon();
		
  /***********关于树图标的渲染*************************/
		
		setLeafIcon(icon);
		setOpenIcon(icon);
		setClosedIcon(icon);
		
  /**********************************************/
		
		return super.getTreeCellRendererComponent(tree, value, sel, expanded,leaf, row, hasFocus);
	}
}