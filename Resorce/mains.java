package Resorce;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


class showFrame extends JFrame implements MouseListener, TreeSelectionListener, TreeWillExpandListener, ActionListener
{
	JFrame mainw;
	JPanel NorthPanel;
	
	JButton upbtn,downbtn,Turn;
	JTextField addressText;
	JLabel addressLabel;
		
	JMenuItem FileNewFolder,FileNewFile,FileDelete,FileRename,FileNature,FileExit;
	JMenu FileMenu,FileNew;
	JMenuItem EditCopy,EditPaste,EditCut,EditSelectAll,EditCancel;
	JMenu EditMenu;
	JMenuItem CheckThumbnail,CheckTile,CheckIcon,CheckList,CheckInformation;
	JMenuItem CheckMenu;
    

	JToolBar Bar1 = new JToolBar();
	JMenuBar Bar2 = new JMenuBar();

	DefaultTreeModel treemodel;
	JTree tree;

	
	File currentfile;
	TreeNode currentnode;
	TreePath currentPath;
	TreePath path ;
	boolean isTable = false;
	
	JSplitPane jsp;
	FileView treeview = new FileView();
	FileSystemView fsv = FileSystemView.getFileSystemView();
	Clipboard  board = getToolkit().getSystemClipboard();
	public showFrame()
	{
		mainw = new JFrame("��Դ������");
		mainw.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
		BuildTree();
		BuildList();
		
		JPanel leftPanel = new JPanel(new BorderLayout());     
        JPanel rightPanel = new JPanel(new BorderLayout());             
        leftPanel.add(new JScrollPane(tree));   
        JScrollPane rightScrollPane = new JScrollPane(table); 
        rightPanel.add(rightScrollPane);   
           
        jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,rightPanel);   
        jsp.setDividerSize(3);   
        jsp.setDividerLocation(200);   
        		
		upbtn = new JButton("����");
		upbtn.setEnabled(false);
		downbtn = new JButton("ǰ��");
		downbtn.setEnabled(false);
		addressText = new JTextField(30);
		addressLabel = new JLabel("��ַ��");
		Turn = new JButton("ת��");
		
		FileNewFolder = new JMenuItem("�½��ļ���");
		FileNewFolder.setEnabled(false);
		FileNewFile = new JMenuItem("�½��ļ�");
		FileNewFile.setEnabled(false);
		FileDelete = new JMenuItem("ɾ��");
		FileDelete.setEnabled(false);
		FileRename = new JMenuItem("������");
		FileRename.setEnabled(false);
		FileNature = new JMenuItem("����");
		FileNature.setEnabled(false);
		FileExit = new JMenuItem("�˳�");
		FileExit.setEnabled(true);
		FileNew = new JMenu("�½�");
		FileMenu = new JMenu("�ļ�");
		
		FileNew.add(FileNewFolder);
		FileNew.add(FileNewFile);
		FileMenu.add(FileDelete);
		FileMenu.add(FileRename);
		FileMenu.add(FileNature);
		FileMenu.addSeparator();
		FileMenu.add(FileNew);
		FileMenu.addSeparator();
		FileMenu.add(FileExit);
		
		EditCopy = new JMenuItem("����");
		EditCopy.setEnabled(false);
		EditPaste = new JMenuItem("ճ��");
		EditPaste.setEnabled(true);
		EditCut = new JMenuItem("����");
		EditCut.setEnabled(false);
		EditSelectAll = new JMenuItem("ȫѡ");
		EditSelectAll.setEnabled(false);
		EditCancel = new JMenuItem("����");
		EditCancel.setEnabled(false);
		EditMenu = new JMenu("�༭");
				
		EditMenu.add(EditCancel);
		EditMenu.addSeparator();
		EditMenu.add(EditCopy);
		EditMenu.add(EditPaste);
		EditMenu.add(EditCut);
		EditMenu.addSeparator();
		EditMenu.add(EditSelectAll);
		
		CheckThumbnail = new JMenuItem("����ͼ");
		CheckThumbnail.setEnabled(true);
		CheckTile = new JMenuItem("ƽ��");
		CheckTile.setEnabled(true);
		CheckIcon = new JMenuItem("ͼ��");
		CheckIcon.setEnabled(true);
		CheckInformation = new JMenuItem("��ϸ��Ϣ");
		CheckInformation.setEnabled(true);
		CheckList = new JMenuItem("�б�");
		CheckList.setEnabled(true);
		CheckMenu = new JMenu("�鿴");
		
		CheckMenu.add(CheckThumbnail);
		CheckMenu.add(CheckIcon);
		CheckMenu.add(CheckList);
		CheckMenu.add(CheckInformation);
		
		
		
		Bar1.add(upbtn);
		Bar1.add(downbtn);
		Bar1.add(addressLabel);
		Bar1.add(addressText);
		Bar1.add(Turn);
		
		Bar2.add(FileMenu);
		Bar2.add(EditMenu);
		Bar2.add(CheckMenu);
		
		NorthPanel = new JPanel();
		NorthPanel.setLayout(new BorderLayout());
		NorthPanel.add(Bar1,BorderLayout.NORTH);
		NorthPanel.add(Bar2,BorderLayout.SOUTH);
		
		mainw.setLayout(new BorderLayout());
		mainw.add(NorthPanel,BorderLayout.NORTH);
		mainw.add(jsp,BorderLayout.CENTER); 
		mainw.setSize(700, 700);
		validate();
		mainw.setLocationRelativeTo(this);   
        mainw.setVisible(true); 
		
        upbtn.addActionListener(this);
        downbtn.addActionListener(this);
        Turn.addActionListener(this);
		FileNewFolder.addActionListener(this);
		FileNewFile.addActionListener(this);
		FileDelete.addActionListener(this);
		FileRename.addActionListener(this);
		FileNature.addActionListener(this);
		FileExit.addActionListener(this);
		EditCopy.addActionListener(this);
		EditPaste.addActionListener(this);
		EditCut.addActionListener(this);
		EditSelectAll.addActionListener(this);
		EditCancel.addActionListener(this);
		CheckThumbnail.addActionListener(this);
		CheckTile.addActionListener(this);
		CheckList.addActionListener(this);
		CheckInformation.addActionListener(this);
		   
	}
	public void BuildTree()
	{		
		TreeNode home = new TreeNode(fsv.getHomeDirectory());
		currentnode = home ;
		Vector rootnode = treeview.GetRootNode();
		for(int i=0 ; i<rootnode.size() ; i++)
		{
			TreeNode root = new TreeNode((File)rootnode.get(i));
			root.add(new TreeNode(""));
			home.add(root);
		}
		treemodel = new DefaultTreeModel(home);
		tree = new JTree(treemodel);
		tree.setRowHeight(20);  //��������Ԫ��߶�          
        tree.getSelectionModel().setSelectionMode(1); //1����SINGLE_TREE_SELECTION��һ��ֻ��ѡ��һ��·��
        
        tree.setCellRenderer(new DefaultTreeCellRenderer());
        tree.putClientProperty("JTree.lineStyle","None");
        tree.addTreeSelectionListener(this);   
        tree.addTreeWillExpandListener(this); 
        tree.addMouseListener(this);
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
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int clickedIndex = table.getSelectedRow();   //��ȡ�����������
        if(clickedIndex >=0)
        {   
        	EditSelectAll.setEnabled(true);
        	SelectAll.setEnabled(true);
            isTable = true;   
            currentfile = tablemodel.getFile(clickedIndex);   
            addressText.setText(currentfile.getAbsolutePath()); //��ַ����ʾ·��  
            if(!currentnode.getFile().getName().equals(fsv.getHomeDirectory()))
            {   
                FileDelete.setEnabled(true);
                Delete.setEnabled(true);
            }   
        }   
        if(e.getClickCount()>=2)
        {   
        	EditSelectAll.setEnabled(true);
        	SelectAll.setEnabled(true);
            if(currentfile.isDirectory())//����������Ŀ¼
            {   
                try
                {   
                    upbtn.setEnabled(true);   
                    if(!tree.isExpanded(currentPath))   
                        tree.expandPath(currentPath); //�����ж�Ӧ�Ľڵ��  
                    if(currentnode.getChildCount()>0)
                    {   
                        for(int i=0;i<currentnode.getChildCount();i++)
                        {   
                            FileTreeNode temp = (FileTreeNode)currentnode.getChildAt(i);//����currentNode���ӽڵ�������ָ�����������ӽڵ㡣   
                            if(temp.GetFile().getPath().equalsIgnoreCase(currentfile.getPath()))//�ж��ӽڵ�·���Ƿ�����б���ѡ�ĵ�·��
                            {   
                                TreeNode[] nodes = treemodel.getPathToRoot(temp);  //��ȡ�Ӹ��ڵ㵽 temp��·��
                                currentPath = new TreePath(nodes);   
                                tree.setSelectionPath(currentPath);  //ѡ���ӽڵ�ĸ��ڵ� 
                                break;   
                            }   
                        }   
                    }   
                }   
                catch(Exception ee)
                {   
                    ee.printStackTrace();   
                }               
            } 
            if(currentfile.isFile())
            {
            	Runtime ce = Runtime.getRuntime();
				String Temp = new String(currentfile.getAbsolutePath());
				String cmdarray = "cmd /c start " + Temp;
				try {
					ce.exec(cmdarray);
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
            }
        }   
        else if(e.getButton() == e.BUTTON3)
        {   
            popup.show(e.getComponent(),e.getX(),e.getY());   
        }   
    }   

	public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException 
	{
		try
		{
			currentnode = (TreeNode)event.getPath().getLastPathComponent();
			if(!event.getPath().toString().equals(fsv.getHomeDirectory()))
			{
				Vector directories = treeview.GetAllDirectories(currentnode.getFile());
				appendNodes(currentnode,directories);
				treemodel.nodeStructureChanged(currentnode);
			}
		}
		catch (Exception ee)
		{
			JOptionPane.showMessageDialog(null,"��ȡ�ļ�����!","��ʾ",JOptionPane.ERROR_MESSAGE);   
            ee.printStackTrace();
		}		
	}
	public void appendNodes(DefaultMutableTreeNode node1, Vector vct)
	{
		Vector newvct = new Vector();
		if(vct!=null)
		{
			newvct = vct;
			node1.removeAllChildren();
		}
		else
		{
			node1.removeAllChildren();
			return;
		}
		for(int i=0; i<vct.size() ; i++)
		{
			File file =(File)newvct.get(i);
			FileTreeNode childnode = new FileTreeNode(file);
			if(file.listFiles()!=null)
			{
				File[] f = file.listFiles();
				for(int j=0 ; j<f.length ; j++)
				{
					if(f[j].isDirectory())
					childnode.add(new FileTreeNode(""));
				}				
			}			       
			node1.add(childnode);
		}
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) 
	{
		// TODO Auto-generated method stub
		try{   
            currentPath = tree.getSelectionPath();   
            currentnode = (FileTreeNode)e.getPath().getLastPathComponent();                   
            currentfile = currentnode.GetFile();   
            tree.scrollPathToVisible(currentPath); //ȷ��·�������е�·�������չ�������һ��·��������⣩���������Ա���ʾcurpath·����ʶ�Ľڵ�  
            addressText.setText(currentnode.GetFile().getAbsolutePath());  //������Ľڵ��·���ڵ�ַ������ʾ���� 
            isTable = false;   

            if(!currentfile.getName().equals("Desktop"))
            {   
            	Vector files = treeview.GetAll(currentnode.GetFile()); //��ȡ����ڵ��µ������ļ�  
                tablemodel.addAllFiles(files);  //����Щ�ļ��ӵ��б���
                upbtn.setEnabled(true); 
                FileNewFolder.setEnabled(true); 
                NewFolder.setEnabled(true);
                FileNewFile.setEnabled(true); 
                FileNature.setEnabled(true);
                FileRename.setEnabled(true);
                EditCut.setEnabled(true);
                EditCopy.setEnabled(true);
                EditPaste.setEnabled(true);
                NewFile.setEnabled(true); 
                Nature.setEnabled(true);
                Rename.setEnabled(true);
                Cut.setEnabled(true);
                Copy.setEnabled(true);
                Paste.setEnabled(true);
                if(!(currentnode.getParent().toString()).equals(File.listRoots())
                		&&(currentnode.toString()!="����")
                		&&(currentnode.toString()!="��")
                		&&(currentnode.toString()!="��ͥ��"))//���Ǹ��ڵ�
                {   
                    FileDelete.setEnabled(true); 
                    Delete.setEnabled(true);
                }   
                else
                {    
                    FileDelete.setEnabled(false); 
                    Delete.setEnabled(true);
                }   
            }   
            else
            {   
            	downbtn.setEnabled(false);
                upbtn.setEnabled(false);   
                FileNewFolder.setEnabled(false); //  �˵����еĲ˵�ѡ��
                FileNewFile.setEnabled(false); 
                FileNature.setEnabled(false);
                FileRename.setEnabled(false);
                FileDelete.setEnabled(false);
                EditCut.setEnabled(false);
                EditCopy.setEnabled(false); 
                NewFolder.setEnabled(false); //  �Ҽ��˵��еĲ˵�ѡ��
                NewFile.setEnabled(false); 
                Nature.setEnabled(false);
                Rename.setEnabled(false);
                Delete.setEnabled(false);
                Cut.setEnabled(false);
                Copy.setEnabled(false);
                currentnode = (TreeNode)treemodel.getRoot();   
                tablemodel.addAllFiles(treeview.GetRoot());   
            }   
        }   
        catch(Exception ee)
        {   
            JOptionPane.showMessageDialog(null,"��ȡ�ļ�����!","Error",JOptionPane.ERROR_MESSAGE);   
            ee.printStackTrace();   
        }   
    }   
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==upbtn)
		{			 
			TreePath p = currentPath.getParentPath();
			path=currentPath;
            if(p != null)
            {                       
                tree.setSelectionPath(p);    
            } 
            downbtn.setEnabled(true);
		}
		if(e.getSource()==downbtn)
		{
			if(path!=null)
			{
				tree.setSelectionPath(path);
			}			
			downbtn.setEnabled(false);
		}
		if(e.getSource()==Turn)
		{
			String text = addressText.getText();
			File f = new File(text);
			tablemodel.addAllFiles(treeview.GetAll(f));
		}
		if(e.getSource()==FileNewFolder||e.getSource()==NewFolder)
		{
		}
		if(e.getSource()==FileNewFile||e.getSource()==FileNewFile)
		{
		}
		if(e.getSource()==FileDelete||e.getSource()==Delete)
		{
		      File f = tablemodel.getFile(table.getSelectedRow());
		      if(f.isFile())
		           f.delete();
		      else if(f.isDirectory())
		      {
		    	  File[] child = f.listFiles();
		    	  for(int i=0 ; i<child.length ; i++)
		    	  {
		    		  child[i].delete();
		    	  }
		    	  f.delete();
		      }
		      refreshNode();
		}
		if(e.getSource()==FileRename||e.getSource()==Rename)
		{
		}
		if(e.getSource()==FileNature||e.getSource()==Nature)
		{
			JOptionPane shuxing = new JOptionPane();
            File currentFile=tablemodel.getFile(table.getSelectedRow());       
            String str;
            if(currentFile.isDirectory())           
            	str="�ļ���·��:"+currentFile.getAbsolutePath()+"\n";             
            else
                str="�ļ�·��:"+currentFile.getAbsolutePath()+"\n";
            String str1="�Ƿ�ɶ�:"+currentFile.canRead()+"\n";
            String str2="�Ƿ��д:"+currentFile.canWrite()+"\n";
            String str3="�ļ��ĳ���:"+currentFile.length()+"M\n";
            SimpleDateFormat s=new SimpleDateFormat("yyyy��MM��dd��HHСʱmm����ss��");
            String str4="�ļ��ϴ��޸ĵ�ʱ��:"+s.format(new Date(currentFile.lastModified()))+"\n";
            String str5="�ļ��Ƿ�����:"+currentFile.isHidden()+"\n";   
            shuxing.showMessageDialog(null,str+str1+str2+str3+str4+str5 ,"����",JOptionPane.ERROR_MESSAGE);
		}
		if(e.getSource()==EditCopy||e.getSource()==Copy)
		{
			
		}
		if(e.getSource()==EditCut||e.getSource()==Cut)
		{
			File f = tablemodel.getFile(table.getSelectedRow());
		      if(f.isFile())
		      {
		    	  StringSelection   text=new   StringSelection(f.getName());
		    	  board.setContents(text,null);
		    	  f.delete();
		      }		           
		      else if(f.isDirectory())
		      {
		    	  StringSelection   text=new   StringSelection(f.getName());
		    	  board.setContents(text,null);
		    	  File[] child = f.listFiles();
		    	  for(int i=0 ; i<child.length ; i++)
		    	  {
		    		  child[i].delete();
		    	  }
		    	  f.delete();
		      }
		      refreshNode();
		}
		if(e.getSource()==EditPaste||e.getSource()==Paste)
		{
			File f = tablemodel.getFile(table.getSelectedRow());
			if(f.isFile())
			{
				String boardfile = board.getName();
				File file = new File(boardfile);
				
			}
		}
		if(e.getSource()==EditSelectAll||e.getSource()==SelectAll)
		{
			table.selectAll();
		}
		if(e.getSource()==EditCancel||e.getSource()==Cancel){}
		if(e.getSource()==CheckThumbnail||e.getSource()==CheckTile
				||e.getSource()==CheckIcon||e.getSource()==CheckList
				||e.getSource()==CheckInformation)
		{
			JOptionPane.showMessageDialog(null, "�ù��ܻ�Ϊ����,�����ڴ���", 
					"��ʾ", JOptionPane.ERROR_MESSAGE);
		}
		if(e.getSource()==FileExit)
		{
			System.exit(0);
		}
		
	}
	
	
	/******************************************************/
	private int index = 0;
	private int pos = 0;
	private int newNodeIndex = 0;
	private Vector<Vector<TreeNode>> copiedList = new Vector<Vector<TreeNode>>();
	private Vector<TreeNode> copyFile = null;	
	private Vector<String> pastedFilePath = new Vector<String>();
	/******************************************************/
	/******************************************************/
	private void setCopyList(TreeNode copiedNode) 	
	{
		if (index++ == 0) {
			copyFile = new Vector<TreeNode>();
			copyFile.add(copiedNode);

			copiedList.add(copyFile);
			pos = copiedNode.getPath().toString().lastIndexOf(File.separator);
		}

		File[] copieFiles = copiedNode.getFile().listFiles();

		if (copieFiles != null) {
			for (File file : copieFiles) {
				if (file.isDirectory()) {
					copyFile = new Vector<TreeNode>();

					TreeNode node_ = new TreeNode(file);
					copyFile.add(node_);
					copiedList.add(copyFile);

					setCopyList(node_);
				} else {
					copyFile = new Vector<TreeNode>();

					TreeNode node_ = new TreeNode(file);
					copyFile.add(node_);
					copiedList.add(copyFile);
				}
			}
		}
	}
	
	public void copy(TreeNode copiedFileNode, TreeNode pastedFileNode) {
		try {
			setCopyList(copiedFileNode);

			for (Vector<TreeNode> copyFile : copiedList) {
				String copyPath = copyFile.get(0).getPath().toString();
				String pastePath = pastedFileNode.getPath()
						+ copyPath.substring(pos);

				pastedFilePath.add(copyPath.substring(pos));

				if (copyFile.get(0).getFile().isFile()) {
					copyFile(copyPath, pastePath);
				} else if (copyFile.get(0).getFile().isDirectory()) {
					new File(pastePath).mkdir();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void copyFile(String copiedPath, String pastedPath) {
		try {
			File file = new File(copiedPath);
			FileInputStream inputStream = new FileInputStream(file);

			File pastedFile = new File(pastedPath);
			FileOutputStream outputStream = new FileOutputStream(pastedFile);

			byte[] buffer = new byte[10240];
			int len = 0;

			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}

			outputStream.close();
			inputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void resetCopiedList() {
		copiedList.removeAllElements();
	}
	/******************************************************/
	/******************************************************/
	public void resetPastedFilePath() {
		pastedFilePath.removeAllElements();
	}
	public Vector<TreeNode> getPastedNode(TreeNode pastedFileNode) {
		Vector<TreeNode> pastedNodeList = new Vector<TreeNode>();

		if (pastedNodeList.size() != 0) {
			pastedNodeList.removeAllElements();
		}

		for (String path : pastedFilePath) {
			String pastedNodePath = pastedFileNode.getPath() + path;
			if (path.lastIndexOf(File.separator) == 0) {
				TreeNode pastedNode = new TreeNode(new File(pastedNodePath));
				pastedNodeList.add(pastedNode);
			}
		}
		return pastedNodeList;
	}
	/******************************************************/
	/******************************************************/
	public String rename(TreeNode node, String newName) {
		if (node != null) {
			File file = node.getFile();
			String path = ((TreeNode) node.getParent()).getPath()
					+ File.separator + newName;

			file.renameTo(new File(path));

			return path;
		} else {
			return "";
		}
	}
	/******************************************************/
	/******************************************************/
	public void createNewFolder(TreeNode node) 
	{
		String name = node.getPath() + File.separator ;
		int index = getNewFolderIndex(name);

		if (index != 0) {
			name = name + "(" + (index + 1) + ")";
		}

		File folder = new File(name);
		folder.mkdir();

		TreeNode newFolder = new TreeNode(folder);
		node.addChild(newFolder);

		newNodeIndex = 0;
	}
	private int getNewFolderIndex(String folder) {
		File file = new File(folder);

		if (file.exists()) {
			newNodeIndex++;
			if (file.getName().indexOf("(") > 0) {
				int postion = file.getPath().lastIndexOf("(");
				String path = file.getPath().substring(0, postion) + "("
						+ (newNodeIndex + 1) + ")";
				getNewFolderIndex(path);
			} else {
				String path = file.getPath() + "(" + (newNodeIndex + 1) + ")";
				getNewFolderIndex(path);
			}
		}

		return newNodeIndex;
	}
	/******************************************************/
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void treeWillCollapse(TreeExpansionEvent event) {}
	public void refreshNode()
    {   
        try
        {   
            Vector files = treeview.GetAll(currentnode.GetFile());   
            tablemodel.addAllFiles(files);   
            appendNodes(currentnode,treeview.GetAllDirectories(currentnode.GetFile()));   
            treemodel.nodeStructureChanged(currentnode);   
        }   
        catch(Exception e)
        {   
            e.printStackTrace();   
        }   
    }   
}
public class mains 
{
	public static void main(String[] args) 
	{
		new showFrame();
	}
}
