package com.monitoring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import com.monitoring.Settings.Settings;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.dom.internal.MouseEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.StatusEvent;
import com.teamdev.jxbrowser.chromium.events.StatusListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;



public class WebPanel extends Thread implements ActionListener
{
	private static final String LS = System.getProperty("line.separator"); 
	
	String siteName = "";
	String siteAddress = "";
	int zoom = 0;
	
	JPanel panel = new JPanel();

	Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
	BrowserView view = new BrowserView(browser);
	
	
	JProgressBar progressBar = new JProgressBar(0, 100);
	Color defaultForgroundColor = progressBar.getForeground();
	
	private long curTime = System.currentTimeMillis();
	private long compTime = System.currentTimeMillis();
	
	Timer timer = new Timer(50, this);

	public WebPanel(String siteName, String siteAddress, int zoom)
	{
		
		this.siteName = siteName;
		this.siteAddress = siteAddress;
		this.zoom = zoom;
		
		curTime = System.currentTimeMillis();
		compTime = System.currentTimeMillis();
		
		panel.setLayout(new BorderLayout());
	    panel.setBorder(BorderFactory.createTitledBorder(null, siteName, TitledBorder.CENTER, TitledBorder.CENTER, new Font("맑은 고딕", Font.BOLD, 16)));
	    
	    //팝업 뜨지마셈
	    browser.setPopupHandler(new PopupHandler() 
	    {
			@Override
			public PopupContainer handlePopup(PopupParams arg0) {
				return null;
			}
		});

	    
	    browser.addStatusListener(new StatusListener() {
			
			@Override
			public void onStatusChange(StatusEvent arg0) {

			}
		});
	    
	    setZoom();
	    progressBar.setValue(0);
	    progressBar.setToolTipText("클릭시 강제 새로고침");
	    progressBar.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				refreshAction();
				
			}
		});

	    panel.add(view, BorderLayout.CENTER);
	    panel.add(progressBar, BorderLayout.SOUTH);
	    
	    view.repaint();
	    
	    start();

	}
	
	@Override
	public void run()
	{
		
		SwingUtilities.invokeLater(new Runnable() 
		{
	        public void run() {
	        	
	        	browser.getCacheStorage().clearCache();
	        	browser.getCookieStorage().deleteAll();
	        	browser.loadURL(siteAddress);
	        	setZoom();
	        }
	      });
		
		timer.start();
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	public BrowserView getBrowserView()
	{
		return view;
	}
	
	public Browser getBrowser()
	{
		return browser;
	}
	
	public void reloadData(String siteName, final String siteAddress, int zoom)
	{
		this.siteName = siteName;
		this.siteAddress = siteAddress;
		this.zoom = zoom;
		
		setSidePanelBorder(siteName);
		
		SwingUtilities.invokeLater(new Runnable() 
		{
	        public void run() {
	        	
	        	browser.getCacheStorage().clearCache();
	        	browser.getCookieStorage().deleteAll();
	        	browser.loadURL(siteAddress);
	        	setZoom();
	        }
	      });
		
		view.revalidate();
		
	}

	private void setZoom() 
	{
		
		browser.addLoadListener(new LoadAdapter() 
		{
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    event.getBrowser().setZoomLevel(zoom);
                    //event.getBrowser().executeJavaScriptAndReturnValue("text-align: center;");
                }
            }
        });
		
		browser.executeJavaScript(
	    		"document.documentElement.style.overflow = 'hidden';" + LS
	    		+ "document.body.scroll = \"no\";" + LS
	    		+ ""
	    		);

	}
	
	public void refreshAction()
	{
		
		SwingUtilities.invokeLater(new Runnable() 
		{
	        public void run() {

	        	browser.getCacheStorage().clearCache();
	        	browser.getCookieStorage().deleteAll();
	        	browser.reload();
	    	    setZoom();
	        }
	      });
		setZoom();
		progressBar.setValue(0);
		compTime = System.currentTimeMillis();
		
		view.revalidate();
	}
	
	private void refresh()
	{
		
		
		curTime = System.currentTimeMillis();
		
		if((curTime - compTime) > Settings.refreshTime && !browser.isLoading())
		{
			refreshAction();
		}
		else if ((curTime - compTime) > Settings.refreshTime*1.5)
		{
			refreshAction();
		}
		else
		{
			Date curDate = new Date(System.currentTimeMillis());
			Date compDate = new Date(compTime);
			
			long intervalTime = curDate.getTime() - compDate.getTime();
			
			long intervalSecond = intervalTime / (10);
			
			progressBar.setMaximum((int) Settings.refreshTime / 10);
			progressBar.setValue((int) (intervalSecond));
			
			//System.out.println(intervalSecond);
			
			if(browser.isLoading())
				progressBar.setForeground(new Color(250,105,0));
			else
				progressBar.setForeground(defaultForgroundColor);
			
			progressBar.setString("다음 새로고침까지 " + ((Settings.refreshTime / 1000) -  intervalSecond/100) + "초");
			
			if(((Settings.refreshTime / 1000) -  intervalSecond/100) < 0)
			{
				progressBar.setForeground(Color.RED);
				progressBar.setString("페이지 로딩이 완료되지 않아 대기 하는 중...");
			}
			
			progressBar.setStringPainted(true);
			progressBar.repaint();
		}
	}

	private void setSidePanelBorder(String title)
	{
		panel.setBorder(BorderFactory.createTitledBorder(null, siteName, TitledBorder.CENTER, TitledBorder.CENTER, new Font("맑은 고딕", Font.BOLD, 16)));
	}
	
	public void removeThis()
	{
		browser = null;
		view.removeAll();
		view = null;
		panel = null;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
	
		if(!Settings.isShowProgressBar)
			progressBar.setVisible(false);
		else
			progressBar.setVisible(true);
		
		if(Settings.isCurrentRefreshing)
			refresh();
		else
		{
			progressBar.setString("중지 됨.");
			progressBar.setForeground(Color.BLACK);
		}
		
	}
	

	
}





