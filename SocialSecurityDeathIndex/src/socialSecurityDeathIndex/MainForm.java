package socialSecurityDeathIndex;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;

/**
 * Simple user interface form to enable loading and querying
 * Social Security Death Index databases.<br>
 * This form contains the <code>main()</code> method for the SSDI application.
 * @author Jim Vlcek
 *
 */
public class MainForm {
	private DataBindingContext m_bindingContext;

	protected Shell shlSsdiDeathMaster;
	private Text txtHostname;
	private Combo cmbDatabaseType;
	private Label mlblFileName;
	private Label mlblCurrentRecord;
	private static MainForm mDefaultWindow;
	private Text text;
	Button mchkAddItems;
	Button mbtnLookItUp;
	private SSDIprogram mModel = null;
	private Text txtPort;
	private Button btnDisconnect;
	private UpdateValueStrategy mBooleanInverterConverter = null;
	private Button btnConnect;
	
	/**
	 * Launch the application.
	 * @param args command line arguments passed to the program (currently ignored)
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					mDefaultWindow = new MainForm();
					mDefaultWindow.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Get the default instance of the form
	 * @return the default instance of the main user interface form
	 */
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
		// Converter for enabling button when a model value is FALSE
		mBooleanInverterConverter = CustomConverters.getBooleanInverter();
		
		mModel = SSDIprogram.Default();
		
		shlSsdiDeathMaster = new Shell();
		shlSsdiDeathMaster.setToolTipText("Utility program for constructing and browsing a Social Security Death Master File database");
		shlSsdiDeathMaster.setSize(450, 300);
		shlSsdiDeathMaster.setText("SSDI Death Master File Browser");
		
		Group grpDatabase = new Group(shlSsdiDeathMaster, SWT.NONE);
		grpDatabase.setText("Database");
		grpDatabase.setBounds(10, 10, 428, 123);
		
		cmbDatabaseType = new Combo(grpDatabase, SWT.NONE);
		cmbDatabaseType.setBounds(10, 20, 125, 29);
		cmbDatabaseType.setItems(new String[] {"MySQL", "SQL Server", "Can of Beans"});
		cmbDatabaseType.setToolTipText("Database type");
		cmbDatabaseType.setText("MySQL");
		
		txtHostname = new Text(grpDatabase, SWT.BORDER);
		txtHostname.setToolTipText("Database server host name or IP address");
		txtHostname.setBounds(141, 22, 100, 27);
		txtHostname.setText("localhost");
		
		btnConnect = new Button(grpDatabase, SWT.NONE);
		btnConnect.setBounds(327, 15, 91, 29);
		btnConnect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mModel.Connect( );
				// mbtnLookItUp.setEnabled(true);
			}
		});
		btnConnect.setToolTipText("Connect to database");
		btnConnect.setText("Connect...");
		
		Label lblQuerySsan = new Label(grpDatabase, SWT.NONE);
		lblQuerySsan.setAlignment(SWT.RIGHT);
		lblQuerySsan.setBounds(10, 78, 85, 21);
		lblQuerySsan.setText("Query SSAN:");
		
		text = new Text(grpDatabase, SWT.BORDER);
		text.setText("999-99-9999");
		text.setBounds(108, 75, 100, 19);
		
		mbtnLookItUp = new Button(grpDatabase, SWT.NONE);
		mbtnLookItUp.setToolTipText("Look up the specified SSAN in the database");
		mbtnLookItUp.setEnabled(false);
		mbtnLookItUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String SSAN = text.getText().replace("-", "").replace(" ", "");
				long lSSAN = Long.parseLong(SSAN);
				IDeathRecord drMatch = mModel.MatchRecord(lSSAN);
				if ( drMatch != null )
					JOptionPane.showMessageDialog(null, drMatch.getGivenName() + " " + drMatch.getSurname());
				else
					JOptionPane.showMessageDialog(null, "No match for SSAN " + String.valueOf( lSSAN ));
			}
		});
		mbtnLookItUp.setBounds(214, 73, 91, 23);
		mbtnLookItUp.setText("Look it up");
		
		txtPort = new Text(grpDatabase, SWT.BORDER | SWT.RIGHT);
		txtPort.setToolTipText("Database port number");
		txtPort.setBounds(245, 22, 76, 27);
		
		btnDisconnect = new Button(grpDatabase, SWT.NONE);
		btnDisconnect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ( mModel != null )
					mModel.Disconnect();
			}
		});
		btnDisconnect.setToolTipText("Disconnect from database");
		btnDisconnect.setText("Disconnect");
		btnDisconnect.setBounds(327, 50, 91, 29);
		
		Group grpMasterFile = new Group(shlSsdiDeathMaster, SWT.NONE);
		grpMasterFile.setText("Master File");
		grpMasterFile.setBounds(10, 139, 428, 129);
		
		Button btnOpen = new Button(grpMasterFile, SWT.NONE);
		btnOpen.setBounds(10, 20, 91, 29);
		btnOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mModel.LoadMasterFile( );
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
		
		mchkAddItems = new Button(grpMasterFile, SWT.CHECK);
		mchkAddItems.setBounds(10, 85, 401, 16);
		mchkAddItems.setText("Add items to database if they don't already exist");
		m_bindingContext = initDataBindings();

		shlSsdiDeathMaster.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) 
			{ 
				if ( mModel != null )
					mModel.Disconnect();
			} 
		} );
	}
	
	/**
	 * Set the path of the Social Security Death Index master file to be loaded.<br>
	 * The value of this property is displayed in a text box in the user interface.
	 * @param sPath
	 */
	public void setMasterFile( String sPath )
	{
		mlblFileName.setText(sPath);
	}
	
	/**
	 * Set the current record under examination in the program<br>
	 * The Social Security Account Number (SSAN) of the current record
	 * is displayed in a text box in the user interface.
	 * @param drCurrent the current {@link socialSecurityDeathIndex#IDeathRecord}
	 * to examine
	 */
	public void setCurrentRecord( IDeathRecord drCurrent )
	{
		mlblCurrentRecord.setText(String.valueOf(drCurrent.getSSAN()));
	}
	
	/**
	 * Initialize the bindings linking data in the program's object model
	 * to the widgets of the main form's user interface
	 * @return an initialized {@link org.eclipse.core.databinding.DataBindingContext}
	 */
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeEnabledMbtnLookItUpObserveWidget = WidgetProperties.enabled().observe(mbtnLookItUp);
		IObservableValue isConnectedMModelObserveValue = BeanProperties.value("isConnected").observe(mModel);
		bindingContext.bindValue(observeEnabledMbtnLookItUpObserveWidget, isConnectedMModelObserveValue, null, null);
		//
		IObservableValue mchkAddItemsObserveSelectionObserveWidget = SWTObservables.observeSelection(mchkAddItems);
		IObservableValue mModelAddRecordsObserveValue = BeansObservables.observeValue(mModel, "addRecords");
		bindingContext.bindValue(mchkAddItemsObserveSelectionObserveWidget, mModelAddRecordsObserveValue, null, null);
		//
		IObservableValue observeTextTxtPortObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtPort);
		IObservableValue databasePortMModelObserveValue = BeanProperties.value("databasePort").observe(mModel);
		bindingContext.bindValue(observeTextTxtPortObserveWidget, databasePortMModelObserveValue, null, CustomConverters.getIntToStringConverter());
		//
		IObservableValue observeEnabledBtnDisconnectObserveWidget = WidgetProperties.enabled().observe(btnDisconnect);
		bindingContext.bindValue(observeEnabledBtnDisconnectObserveWidget, isConnectedMModelObserveValue, null, null);
		//
		IObservableValue observeTextCmbDatabaseTypeObserveWidget = WidgetProperties.text().observe(cmbDatabaseType);
		IObservableValue databaseTypeMModelObserveValue = BeanProperties.value("databaseType").observe(mModel);
		bindingContext.bindValue(observeTextCmbDatabaseTypeObserveWidget, databaseTypeMModelObserveValue, null, null);
		//
		IObservableValue observeEnabledMchkAddItemsObserveWidget = WidgetProperties.enabled().observe(mchkAddItems);
		bindingContext.bindValue(observeEnabledMchkAddItemsObserveWidget, isConnectedMModelObserveValue, null, null);
		//
		IObservableValue btnConnectObserveEnabledObserveWidget = SWTObservables.observeEnabled(btnConnect);
		bindingContext.bindValue(btnConnectObserveEnabledObserveWidget, isConnectedMModelObserveValue, mBooleanInverterConverter, mBooleanInverterConverter ); // null, null);
		//
		return bindingContext;
	}
}
