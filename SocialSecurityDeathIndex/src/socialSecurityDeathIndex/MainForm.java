package socialSecurityDeathIndex;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class MainForm {

	protected Shell shlSsdiDeathMaster;
	private Text txtHostname;
	private Combo cmbDatabaseType;
	private Label mlblFileName;
	private Label mlblCurrentRecord;
	private static MainForm mDefaultWindow;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			mDefaultWindow = new MainForm();
			mDefaultWindow.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MainForm getDefault()
	{
		return mDefaultWindow;
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlSsdiDeathMaster.open();
		shlSsdiDeathMaster.layout();
		while (!shlSsdiDeathMaster.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSsdiDeathMaster = new Shell();
		shlSsdiDeathMaster.setToolTipText("Utility program for constructing and browsing a Social Security Death Master File database");
		shlSsdiDeathMaster.setSize(450, 300);
		shlSsdiDeathMaster.setText("SSDI Death Master File Browser");
		
		Group grpDatabase = new Group(shlSsdiDeathMaster, SWT.NONE);
		grpDatabase.setText("Database");
		grpDatabase.setBounds(10, 10, 428, 109);
		
		cmbDatabaseType = new Combo(grpDatabase, SWT.NONE);
		cmbDatabaseType.setBounds(10, 20, 125, 29);
		cmbDatabaseType.setItems(new String[] {"MySQL", "SQL Server"});
		cmbDatabaseType.setToolTipText("Database type");
		cmbDatabaseType.setText("MySQL");
		
		txtHostname = new Text(grpDatabase, SWT.BORDER);
		txtHostname.setBounds(141, 22, 100, 27);
		txtHostname.setText("localhost");
		
		Button btnConnect = new Button(grpDatabase, SWT.NONE);
		btnConnect.setBounds(247, 20, 91, 29);
		btnConnect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				SSDIprogram.Connect( cmbDatabaseType.getText() );
			}
		});
		btnConnect.setToolTipText("Connect to database");
		btnConnect.setText("Connect...");
		
		Group grpMasterFile = new Group(shlSsdiDeathMaster, SWT.NONE);
		grpMasterFile.setText("Master File");
		grpMasterFile.setBounds(10, 125, 428, 89);
		
		Button btnOpen = new Button(grpMasterFile, SWT.NONE);
		btnOpen.setBounds(10, 20, 91, 29);
		btnOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				SSDIprogram.LoadMasterFile();
			}
		});
		btnOpen.setToolTipText("Open an SSDI Death Master File");
		btnOpen.setText("Open...");
		
		mlblFileName = new Label(grpMasterFile, SWT.NONE);
		mlblFileName.setBounds(124, 32, 287, 17);
		mlblFileName.setText("No file loaded...");
		
		Label lblCurrentRecordBanner = new Label(grpMasterFile, SWT.NONE);
		lblCurrentRecordBanner.setBounds(10, 62, 108, 17);
		lblCurrentRecordBanner.setText("Current record:");
		
		mlblCurrentRecord = new Label(grpMasterFile, SWT.NONE);
		mlblCurrentRecord.setBounds(124, 62, 287, 17);

	}
	
	public void setMasterFile( String sPath )
	{
		mlblFileName.setText(sPath);
	}
	
	public void setCurrentRecord( String sRecord )
	{
		mlblCurrentRecord.setText(sRecord);
	}
}
