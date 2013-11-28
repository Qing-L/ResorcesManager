package Resorce;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableRender implements TableCellRenderer  
{
	private JLabel celItem;
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) 
	{
		// TODO Auto-generated method stub
		FileView item = (FileView)value;   
        celItem = new JLabel(item.GetIcon());   
        celItem.setText((item.GetName().length() == 0)? item.GetFile().getPath():item.GetName());   
        celItem.setHorizontalAlignment(celItem.LEFT);     
        return celItem;
	}   

}
