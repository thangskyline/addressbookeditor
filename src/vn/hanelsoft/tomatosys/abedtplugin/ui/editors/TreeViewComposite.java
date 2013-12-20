package vn.hanelsoft.tomatosys.abedtplugin.ui.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeViewComposite extends Composite implements TreeListener,
		SelectionListener {

	Text txtName, txtEmail;

	public TreeViewComposite(Composite parent) {
		super(parent, SWT.NONE);
		// TODO Auto-generated constructor stub
		createUI();
	}

	public void createUI() {
		// set layout for parent
		GridLayout gl = new GridLayout(2, false);
		gl.horizontalSpacing = 15;
		gl.marginTop = 5;
		gl.marginBottom = 5;

		this.setLayout(gl);

		// add group to left parent container
		Group grp1 = new Group(this, SWT.NONE);
		GridData gd1 = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd1.widthHint = 300;
		gd1.heightHint = 300;
		grp1.setLayoutData(gd1);
		grp1.setLayout(new FillLayout());

		// add treeview to group 1
		int style = SWT.MULTI | SWT.NONE;
		final Tree t = new Tree(grp1, style);
//		t.setLocation(40, 40);
		
		for (int i = 0; i < 5; i++) {
			TreeItem treeItem = new TreeItem(t, SWT.NONE);
			treeItem.setText("TreeItem" + i);
			for (int j = 0; j < 5; j++) {
				TreeItem subTreeItem = new TreeItem(treeItem, SWT.NONE);
				subTreeItem.setText("SubTreeItem" + j);
				for (int k = 0; k < 5; k++) {
					TreeItem subSubTreeItem = new TreeItem(subTreeItem,
							SWT.NONE);
					subSubTreeItem.setText("SubSubTreeItem" + k);
				}
			}
		}

		// add listener for tree
		t.addTreeListener(this);
		t.addSelectionListener(this);
		t.pack();
		grp1.pack();

		// Layout and add group 2 to right parent container
		Group grp2 = new Group(this, SWT.NONE);
		GridData gd2 = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd2.widthHint = 450;
		// gd2.heightHint = 350;
		grp2.setLayoutData(gd2);

		GridLayout gl4group2 = new GridLayout(2, false);
		gl4group2.horizontalSpacing = 15;
		gl4group2.marginTop = 15;
		gl4group2.marginLeft = 15;
		grp2.setLayout(gl4group2);

		// layout for each element of group 2
		GridData gd3 = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd3.widthHint = 50;
		GridData gd4 = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd4.widthHint = 200;

		Label lblName = new Label(grp2, SWT.NONE);
		lblName.setText("Name");
		lblName.setLayoutData(gd3);
		lblName.pack();

		txtName = new Text(grp2, SWT.BORDER);
		txtName.setLayoutData(gd4);
		txtName.pack();

		Label lblEmail = new Label(grp2, SWT.NONE);
		lblEmail.setText("Email");
		lblEmail.setLayoutData(gd3);
		lblEmail.pack();

		txtEmail = new Text(grp2, SWT.BORDER);
		txtEmail.setLayoutData(gd4);
		txtEmail.pack();

		grp2.pack();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		TreeItem ti = (TreeItem) e.item;
		handlerTreeNodeClick(ti.getText());
	}

	@Override
	public void treeCollapsed(TreeEvent e) {
		// TODO Auto-generated method stub
		TreeItem ti = (TreeItem) e.item;
	}

	@Override
	public void treeExpanded(TreeEvent e) {
		// TODO Auto-generated method stub
		TreeItem ti = (TreeItem) e.item;
	}

	private void handlerTreeNodeClick(String type) {
		clearResult();
		if (type.equals("TreeItem1")) {
			txtName.setText("Name 1");
			txtEmail.setText("Email 1");
		}
		if (type.equals("TreeItem2")) {
			txtName.setText("Name 2");
			txtEmail.setText("Email 2");
		}
		if (type.equals("TreeItem3")) {
			txtName.setText("Name 2A");
			txtEmail.setText("Email 2A");
		}
		if (type.equals("TreeItem4")) {
			txtName.setText("Name 2B");
			txtEmail.setText("Email 2B");
		}
		if (type.equals("TreeItem5")) {
			txtName.setText("Name 3");
			txtEmail.setText("Email 3");
		}

	}

	private void clearResult() {
		txtName.setText("");
		txtEmail.setText("");
	}
}