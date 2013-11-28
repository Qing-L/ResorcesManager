package Resorce;

import java.io.File;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

@SuppressWarnings("serial")
public class TreeNode  implements MyNode
{
	/*************私有数据*******************/
	
	private Vector<File> allFiles = new Vector<File>();
	private Vector<File> folder = new Vector<File>();
	private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	private File file = null;
	private long dirLenth = 0;
	
	/*************构造函数*******************/
	
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
	
	/*************功能函数*******************/
	
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
/************关于树节点的接口增加其功能********/
/**************************************/
interface MyNode 
{
    //获得子节点，filekind子节点的类型——文件/文件夹，子节点的索引
	public abstract Object getChild(String fileKind, int index);
    //获得根节点
	public abstract Object getRoot();
    //获得的节点的filekind类型文件/文件夹的个数
	public abstract int getChildCount(String fileKind);
    //获得节点的文件的系统名字
	public abstract String toString();
	//获得节点的文件的系统图标
	public abstract Icon getIcon();
    //判断节点是否是子节点
	public abstract boolean isLeaf();
    //取得节点的路径
	public abstract String getPath();
    //取得节点的父节点
	public abstract Object getParent();
    //取得当前节点
	public abstract Object getCurrent();
    //增加当前节点的子节点
	public abstract void addChild(MyNode node);
    //获取节点的文件
	public abstract File getFile();
    //删除节点的子节点
	public abstract void deleteChild(MyNode node);
    //获得节点的文件的大小
	public abstract long getSize();
	//移除所有节点
	public abstract void removeAllChildren();
}


