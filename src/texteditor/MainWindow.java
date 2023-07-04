package texteditor;

import java.awt.*;
import javax.swing.*;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;


public class MainWindow extends javax.swing.JFrame{
	

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Color mbg= new Color(0xFFFFFF);
	public ImageIcon icon= new ImageIcon("res/c_icon.png");
	public ImageIcon open= new ImageIcon("res/open.png");
	public ImageIcon save= new ImageIcon("res/save.png");
	public ImageIcon tcut= new ImageIcon("res/cut.png");
	public ImageIcon tcopy= new ImageIcon("res/copy.png");
	public ImageIcon tpaste= new ImageIcon("res/paste.png");
	public ImageIcon boticon= new ImageIcon("res/boticon.png");
	public ImageIcon Aa= new ImageIcon("res/Aa.png");
	public ImageIcon moon= new ImageIcon("res/moon.png");
	public ImageIcon exitapp= new ImageIcon("res/exit.png");
	public JTextPane textPane = new JTextPane();
	public String selectedText, getText, fileName, text;
	public JFileChooser jfile_open=new JFileChooser("Open");
	public static int i, j;
	public static int len;
	public static char ch;
	public static char ch2;
	public int ifile;
	public static String Laf="com.formdev.flatlaf.themes.FlatMacLightLaf";
	
	
	//MAIN FUNCTIONS
		public void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setSize(1300, 800);
        setTitle("SnowflakesEditor");
        setIconImage(icon.getImage());
        getContentPane().setBackground(mbg);
        
        
        
        JScrollPane textScroll=new JScrollPane(textPane);
        getContentPane().add(textScroll, BorderLayout.CENTER);
        
        JToolBar toolBar = new JToolBar();
        getContentPane().add(toolBar, BorderLayout.NORTH);
        
        JToggleButton tglTheme = new JToggleButton();
        tglTheme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tglTheme.isSelected()) {
                    try {
                        UIManager.setLookAndFeel(new FlatMacDarkLaf());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        UIManager.setLookAndFeel(new FlatMacLightLaf());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                SwingUtilities.updateComponentTreeUI(MainWindow.this);
            }
        });
        toolBar.add(tglTheme);
        tglTheme.setIcon(moon);
                
        JButton cut = new JButton("");
        cut.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		selectedText = textPane.getSelectedText();
        		if (selectedText != null) {
                    StringSelection stringSelection = new StringSelection(selectedText);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                    textPane.replaceSelection("");
                }

        	}
        });
        cut.setToolTipText("Cut");
        
        cut.setIcon(tcut);
        toolBar.add(cut);
        
        JButton copy = new JButton("");
        copy.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		selectedText = textPane.getSelectedText();
        		if (selectedText != null) {
                    StringSelection stringSelection = new StringSelection(selectedText);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }

        	}
        });
        copy.setToolTipText("Copy");
        
        copy.setIcon(tcopy);
        toolBar.add(copy);
        
        JButton paste = new JButton("");
        paste.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		selectedText = textPane.getSelectedText();
        
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable contents = clipboard.getContents(null);
                    if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        
                            try {
								text = (String) contents.getTransferData(DataFlavor.stringFlavor);
							} catch (UnsupportedFlavorException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                        textPane.replaceSelection(text);
                    }
                

        	}
        });
        paste.setToolTipText("Paste");
        
        paste.setIcon(tpaste);
        toolBar.add(paste);
        
        JButton wordUpcase = new JButton("");
        wordUpcase.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		selectedText = textPane.getSelectedText();
        		if (selectedText != null) {
        			System.out.println("Aa ACTIVATED");
        			text=MainWindow.upCase(selectedText);
        			textPane.replaceSelection(text);
                }

        	}
        });
        wordUpcase.setToolTipText("Capitalize First Letter");
        
        wordUpcase.setIcon(Aa);
        toolBar.add(wordUpcase);
        
        
        
	}
		
		public MainWindow() {
		
		JMenuBar menu_bar = new JMenuBar();
		setJMenuBar(menu_bar);
		
		JMenu menu_file = new JMenu("File");
		menu_bar.add(menu_file);
		
		JMenuItem file_open = new JMenuItem("Open");
		//OPEN FILE
		file_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ifile= jfile_open.showOpenDialog(null);
				if(ifile== JFileChooser.APPROVE_OPTION) {
				File file=new File(jfile_open.getSelectedFile().toString());
				fileName=file.getName();
				setTitle("Snowflakes Editor - "+fileName);
				try {
					FileReader reader = new FileReader(file);
					BufferedReader b_reader= new BufferedReader(reader);
					try {
						textPane.read(b_reader, null);
						b_reader.close();
						textPane.requestFocus();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				else {
					JOptionPane.showMessageDialog(null, "Snowflaked Editor: Uhm, I didn't get any file! \n Please try again! \n ERROR CODE: NOFILE001", "ERROR - No file selected", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//end of OPEN FILE
		
		file_open.setIcon(open);
		menu_file.add(file_open);
		
		JMenuItem file_save = new JMenuItem("Save");
		file_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("SAVE");			}
		});
		
		file_save.setIcon(save);
		menu_file.add(file_save);
		
		JMenuItem file_saveas = new JMenuItem("Save As");
		file_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("SAVE AS");			}
		});
		
		file_saveas.setIcon(save);
		menu_file.add(file_saveas);
		
		JMenuItem file_exit = new JMenuItem("Exit");
		file_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);			}
		});
		file_exit.setIcon(exitapp);
		menu_file.add(file_exit);
		
        System.out.println("MainWindow Constructor Responding - STAGE 1 - 3");
        initComponents();
    }
		
	public static void main(String args[]) {
		FlatMacLightLaf.registerCustomDefaultsSource("style");
		try {
			UIManager.setLookAndFeel(Laf);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("MainWindow main Function responding - STAGE 1 - 2");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
	}
	
	//GENERAL FUNCTIONS 
	public static String upCase(String getText) {
		
		int len = getText.length();
		getText=Character.toUpperCase(getText.charAt(0))+getText.substring(1, len);
		String upcase="";
		for(i=0; i<len-1; i++) {
			
			ch=getText.charAt(i);
			if(i!=len-1) {
				if(ch==' ' && getText.charAt(i+1) !=' ') {
				
				ch2=getText.charAt(i+1);
				upcase=getText.substring(0, i+1)+Character.toUpperCase(ch2)+getText.substring(i+2, len);
			}
		}
		}
		System.out.println("getText");
		return upcase;
	}
	
}