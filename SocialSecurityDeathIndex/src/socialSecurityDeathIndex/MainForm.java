package socialSecurityDeathIndex;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;

public class MainForm {

	protected Shell shlSsdiDeathMaster;
	private Text txtHostname;
	private Combo cmbDatabaseType;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainForm window = new MainForm();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
		Button btnConnect = new Button(shlSsdiDeathMaster, SWT.NONE);
		btnConnect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				SSDIprogram.Connect( cmbDatabaseType.getText() );
			}
		});
		btnConnect.setToolTipText("Connect to database");
		btnConnect.setBounds(247, 10, 91, 29);
		btnConnect.setText("Connect...");
		
		cmbDatabaseType = new Combo(shlSsdiDeathMaster, SWT.NONE);
		cmbDatabaseType.setItems(new String[] {"MySQL", "SQL Server"});
		cmbDatabaseType.setToolTipText("Database type");
		cmbDatabaseType.setBounds(10, 10, 125, 29);
		cmbDatabaseType.setText("MySQL");
		
		Button btnOpen = new Button(shlSsdiDeathMaster, SWT.NONE);
		btnOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				SSDIprogram.LoadMasterFile();
			}
		});
		btnOpen.setToolTipText("Open an SSDI Death Master File");
		btnOpen.setBounds(347, 10, 91, 29);
		btnOpen.setText("Open...");
		
		txtHostname = new Text(shlSsdiDeathMaster, SWT.BORDER);
		txtHostname.setText("localhost");
		txtHostname.setBounds(141, 10, 100, 27);

	}
}
