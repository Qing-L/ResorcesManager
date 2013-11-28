package Resorce;

import java.io.File;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

@SuppressWarnings("serial")
public class TreeNode  implements MyNode
{
	/*************˽������*******************/
	
	private Vector<File> allFiles = new Vector<File>();
	private Vector<File> folder = new Vector<File>();
	private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	private File file = null;
	private long dirLenth = 0;
	
	/*************���캯��*******************/
	
	public TreeNode() 
	{
		file = fileSystemView.getHomeDirectory();
		addChildren();
	}
	public TreeNode(File file)
	{
		this.file = file;
		addChildren();
	}
	public TreeNode(String name)
	{
		this.file = new File(name);
		addChildren();
	}
	
	/*************���ܺ���*******************/
	
	public void addChild(MyNode node) 
	{
		allFiles.add(node.getFile());
	}
	private void addChildren() 
	{
		File[] fileList = fileSystemView.getFiles(file, true);

		for (File file : fileList) {
			allFiles.add(file);

			if (file.isDirectory()
					&& !file.getName().toLowerCase().endsWith(".lnk")) {
				folder.add(file);
			}
		}
	}
	public int getChildCount(String fileKind) 
	{
		if (fileKind.equals("FILE")) 
		{
			return allFiles.size();
		} else if (fileKind.equals("FOLDER")) 
		{
			return folder.size();
		} else {
			return 0;
		}

	}
	public Object getChild(String fileKind, int index) 
	{
		if (fileKind.equals("FILE")) {
			return new TreeNode(allFiles.get(index));
		} else if (fileKind.equals("FOLDER")) {
			return new TreeNode(folder.get(index));
		} else {
			return null;
		}

	}
	public Object getCurrent() 
	{
		return this;
	}
	public File getFile() 
	{
		return this.file;
	}
	public void deleteChild(MyNode node) 
	{
		allFiles.remove(node.getFile());
		System.out.println(allFiles.size());
	}
	public long getSize() 
	{
		if (this.getFile().isDirectory()) 
		{
			return getDirSize(this.getFile().getPath());
		} 
		else 
		{
			return this.getFile().length();
		}
	}
	private long getDirSize(String dir) 
	{
		File[] fileList = new File(dir).listFiles();

		for (File file : fileList) {
			if (file.isDirectory()) {
				getDirSize(file.getPath());
			} else {
				dirLenth = dirLenth + file.length();
			}
		}

		return dirLenth;
	}
	public void removeAllChildren() 
	{
		this.allFiles.removeAllElements();
	}
	public Icon getIcon() 
	{
		return fileSystemView.getSystemIcon(file);
	}
	public Object getRoot() 
	{
		return this;
	}
	public boolean isLeaf() 
	{
		return folder.size() == 0;
	}
	public String getPath() 
	{
		return this.file.getPath();
	}
	public Object getParent() 
	{
		return new TreeNode(file.getParentFile());
	}

}
/**************************************/
/************�������ڵ�Ľӿ������书��********/
/**************************************/
interface MyNode 
{
    //����ӽڵ㣬filekind�ӽڵ�����͡����ļ�/�ļ��У��ӽڵ������
	public abstract Object getChild(String fileKind, int index);
    //��ø��ڵ�
	public abstract Object getRoot();
    //��õĽڵ��filekind�����ļ�/�ļ��еĸ���
	public abstract int getChildCount(String fileKind);
    //��ýڵ���ļ���ϵͳ����
	public abstract String toString();
	//��ýڵ���ļ���ϵͳͼ��
	public abstract Icon getIcon();
    //�жϽڵ��Ƿ����ӽڵ�
	public abstract boolean isLeaf();
    //ȡ�ýڵ��·��
	public abstract String getPath();
    //ȡ�ýڵ�ĸ��ڵ�
	public abstract Object getParent();
    //ȡ�õ�ǰ�ڵ�
	public abstract Object getCurrent();
    //���ӵ�ǰ�ڵ���ӽڵ�
	public abstract void addChild(MyNode node);
    //��ȡ�ڵ���ļ�
	public abstract File getFile();
    //ɾ���ڵ���ӽڵ�
	public abstract void deleteChild(MyNode node);
    //��ýڵ���ļ��Ĵ�С
	public abstract long getSize();
	//�Ƴ����нڵ�
	public abstract void removeAllChildren();
}


