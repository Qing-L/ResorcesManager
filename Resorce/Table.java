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
		SelectAll = new JMenuItem("ȫѡ");
		SelectAll.setEnabled(false);
		Delete = new JMenuItem("ɾ��");
		Delete.setEnabled(false);
		Copy = new JMenuItem("����");
		Copy.setEnabled(false);
		Paste = new JMenuItem("ճ��");
		Paste.setEnabled(true);
		Cut = new JMenuItem("����");
		Cut.setEnabled(false);
		Cancel = new JMenuItem("����");
		Cancel.setEnabled(false);
		Rename = new JMenuItem("������");
		Rename.setEnabled(false);
		Nature = new JMenuItem("����");
		Nature.setEnabled(false);
		NewFolder = new JMenuItem("�½��ļ���");
		NewFolder.setEnabled(false);
		NewFile = new JMenuItem("�½��ļ�");
		NewFile.setEnabled(false);
		PopupNew = new JMenu("�½�");
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
		String[] colnames = {"����","��С","����","�޸�ʱ��"};
		tablemodel = new TableModel();			
		tablemodel.addAllFiles(treeview.GetRoot());
		table = new JTable(tablemodel);
		
		table.setShowHorizontalLines(false); //����ʾ�б��ˮƽ������ 
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
