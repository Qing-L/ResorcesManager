package Resorce;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Table implements ActionListener, MouseListener 
{
	JMenuItem SelectAll,Delete,Copy,Cut,Paste,Cancel,NewFolder,NewFile,Rename,Nature;
    JMenu PopupNew;
	JPopupMenu popup;
	
	JTable table;
	TableModel tablemodel;
	
	public Table()
	{
		SelectAll = new JMenuItem("全选");
		SelectAll.setEnabled(false);
		Delete = new JMenuItem("删除");
		Delete.setEnabled(false);
		Copy = new JMenuItem("复制");
		Copy.setEnabled(false);
		Paste = new JMenuItem("粘贴");
		Paste.setEnabled(true);
		Cut = new JMenuItem("剪切");
		Cut.setEnabled(false);
		Cancel = new JMenuItem("撤销");
		Cancel.setEnabled(false);
		Rename = new JMenuItem("重命名");
		Rename.setEnabled(false);
		Nature = new JMenuItem("属性");
		Nature.setEnabled(false);
		NewFolder = new JMenuItem("新建文件夹");
		NewFolder.setEnabled(false);
		NewFile = new JMenuItem("新建文件");
		NewFile.setEnabled(false);
		PopupNew = new JMenu("新建");
		popup = new JPopupMenu();
		
		PopupNew.add(NewFolder);
		PopupNew.add(NewFile);
		
		popup.add(SelectAll);
		popup.add(Delete);
		popup.addSeparator();
		popup.add(Copy);
		popup.add(Paste);
		popup.add(Cut);
		popup.add(Cancel);
		popup.addSeparator();
		popup.add(PopupNew);
		popup.addSeparator();
		popup.add(Nature);
		popup.add(Rename);
		
		SelectAll.addActionListener(this);
		NewFolder.addActionListener(this);
		NewFile.addActionListener(this);
		Delete.addActionListener(this);
		Rename.addActionListener(this);
		Nature.addActionListener(this);
		Copy.addActionListener(this);
		Paste.addActionListener(this);
		Cut.addActionListener(this);
		SelectAll.addActionListener(this);
		Cancel.addActionListener(this);  
	}
	
	public void BuildList()
	{		
		String[] colnames = {"名称","大小","类型","修改时间"};
		tablemodel = new TableModel();			
		tablemodel.addAllFiles(treeview.GetRoot());
		table = new JTable(tablemodel);
		
		table.setShowHorizontalLines(false); //不显示列表的水平网格线 
		table.getColumnModel().getColumn(0).setPreferredWidth(300);   
		table.getColumnModel().getColumn(3).setPreferredWidth(150); 
        table.setSelectionBackground(Color.BLUE);   
        table.setSelectionForeground(Color.WHITE);   
        table.setRowHeight(20);   
        TableColumnModel columnModel = table.getColumnModel();   
        TableColumn columnName = columnModel.getColumn(0);
        columnName.setCellRenderer(new TableRender());
        for(int i=0 ; i<4 ; i++)
        {
        	table.getColumnModel().getColumn(i).setHeaderValue(colnames[i]);
        }
        
        table.addMouseListener(this);
	}

}
