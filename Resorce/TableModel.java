package Resorce;

import java.awt.Component;
import java.io.File;
import java.sql.Date;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class TableModel extends AbstractTableModel
{
	Vector file  = new Vector();
	
	public Object getValueAt(int row, int col) 
    {    
        File clickFile = (File)file.get(row);   
        switch (col)
        {   
            case 0 :return new FileView(clickFile);
            case 1 :return new Long(clickFile.length());   
            case 2 :return isDir(clickFile);   
            case 3 :
            {
            	Date date = new Date(clickFile.lastModified());
            	date.setTime(clickFile.lastModified());
            	return date; 
            }  
        }   
        return null;   
    }   
	
	public String isDir(File file)
    {   
        if(file.isDirectory())
        {   
            return "文件夹";   
        }   
        else if(file.isFile())
        {   
            return "文件";   
        }   
        else return "未知";   
    }   
	
	public File getFile(int row)
	{
		return (File)file.get(row);
	}
	
	public void addAllFiles(Vector all)
	{
		file.clear();
		if(all!=null)
		{
			file.addAll(all);
		}
		this.fireTableDataChanged();
	}
	@Override
	public int getRowCount() 
	{
		return file.size();
	}
	@Override
	public int getColumnCount() 
	{
		return 4;
	}
	
}
