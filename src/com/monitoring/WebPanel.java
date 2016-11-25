package com.monitoring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import com.monitoring.Settings.Settings;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.CertificateErrorParams;
import com.teamdev.jxbrowser.chromium.LoadHandler;
import com.teamdev.jxbrowser.chromium.LoadParams;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.events.FailLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FrameLoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadListener;
import com.teamdev.jxbrowser.chromium.events.ProvisionalLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.RenderEvent;
import com.teamdev.jxbrowser.chromium.events.RenderListener;
import com.teamdev.jxbrowser.chromium.events.StartLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.StatusEvent;
import com.teamdev.jxbrowser.chromium.events.StatusListener;
import com.teamdev.jxbrowser.chromium.internal.ipc.message.IsLoadingMessage;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;



public class WebPanel extends Thread implements ActionListener
{
	private static final String LS = System.getProperty("line.separator"); 
	
	String siteName = "";
	String siteAddress = "";
	
	JPanel panel = new JPanel();

	Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
	BrowserView view = new BrowserView(browser);
	
	JProgressBar progressBar = new JProgressBar(0, 100);
	Color defaultForgroundColor = progressBar.getForeground();
	
	private long curTime = System.currentTimeMillis();
	private long compTime = System.currentTimeMillis();
	
	Timer timer = new Timer(50, this);

	public WebPanel(String siteName, String siteAddress)
	{
		
		this.siteName = siteName;
		this.siteAddress = siteAddress;
		
		curTime = System.currentTimeMillis();
		compTime = System.currentTimeMillis();
		
		panel.setLayout(new BorderLayout());
	    panel.setBorder(BorderFactory.createTitledBorder(null, siteName, TitledBorder.CENTER, TitledBorder.CENTER, new Font("맑은 고딕", Font.BOLD, 16)));
	    
	    //팝업 조까
	    browser.setPopupHandler(new PopupHandler() {
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

	    panel.add(view, BorderLayout.CENTER);
	    panel.add(progressBar, BorderLayout.SOUTH);
	    
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

	private void setZoom() 
	{
		
		browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    event.getBrowser().setZoomLevel(-5);
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
	
	private void refreshAction()
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
				progressBar.setForeground(Color.RED);
			else
				progressBar.setForeground(defaultForgroundColor);
			
			progressBar.setString("다음 새로고침까지 " + ((Settings.refreshTime / 1000) -  intervalSecond/100) + "초");
			
			if(((Settings.refreshTime / 1000) -  intervalSecond/100) < 0)
				progressBar.setString("페이지 로딩이 완료되지 않아 대기 하는 중...");
			
			progressBar.setStringPainted(true);
			progressBar.repaint();
		}
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
			progressBar.setForeground(Color.BLACK);
		
	}
	

	
}





