package Resorce;

import java.io.File;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class FileView 
{
	/************私有数据******************/
	
	private File file;
	private Icon icon;
	
	/***********构造函数*******************/
	
	public FileView()
	{
		
	}
	public FileView(File file)
	{
		this.file = file;
	}
	
	/***********函数功能*******************/
	
	public Vector GetRootNode()
	{
		FileSystemView  fileview = FileSystemView.getFileSystemView();
		File [] rootf = fileview.getFiles(fileview.getHomeDirectory(),true);
		if(rootf.length>0)
		{
			Vector Roots = new Vector();
			for(int i=0; i<rootf.length ; i++)
			{
				if(rootf[i].isDirectory()&& !rootf[i].toString().toLowerCase().endsWith(".lnk"))
				Roots.add(rootf[i]);
			}
			return Roots;
		}
		return null;
	}
	public Vector GetRoot()
	{
		FileSystemView  fileview = FileSystemView.getFileSystemView();
		File [] rootf = fileview.getFiles(fileview.getHomeDirectory(),true);
		if(rootf.length>0)
		{
			Vector Roots = new Vector();
			for(int i=0; i<rootf.length ; i++)
			{
				Roots.add(rootf[i]);
			}
			return Roots;
		}
		return null;
	}
	
	public Vector GetAll(File file)
	{
		File [] allfiles =file.listFiles();
		if(allfiles!=null&&allfiles.length>0)
		{
			Vector Directories = new Vector();
			Vector Files = new Vector();
			for(int i=0; i<allfiles.length ; i++)
			{
				if(allfiles[i].isDirectory()&&!allfiles[i].isHidden())
				{
					Directories.add(allfiles[i]);
				}
				else if(allfiles[i].isFile()&&!allfiles[i].isHidden())
				{
					Files.add(allfiles[i]);
				}
			}
			for(int i=0 ; i<Files.size() ; i++)
			{
				Directories.add(Files.get(i));
			}
			return Directories;
		}
		return null;
	}
	
	public Vector GetAllDirectories(File file)
	{
		File [] files = file.listFiles();//获取所有目录   
        if(files != null && files.length >0)
        {   
            Vector allDirectories = new Vector();   
            for (int i=0;i<files.length;i++)
            {   
                if(files[i].isDirectory() && !files[i].isHidden())
                {   
                    allDirectories.add(files[i]);   
                }   
            }   
            return allDirectories;   
        }   
        return null;   
	}
	
	/**********获取文件相关资源*****************/
	
	public Icon GetIcon()
	{
		return FileSystemView.getFileSystemView().getSystemIcon(file);
	}
	public String GetName()
	{
		return FileSystemView.getFileSystemView().getSystemDisplayName(file);
	}
	public File GetFile()
	{
		return this.file;
	}
}
