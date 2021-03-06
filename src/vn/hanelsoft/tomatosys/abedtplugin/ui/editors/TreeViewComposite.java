package vn.hanelsoft.tomatosys.abedtplugin.ui.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeViewComposite extends Composite implements TreeListener,
		SelectionListener {

	model.Group rootGroup;
	model.Entry lastSelectedData;

	Text txtName, txtEmail;
	Tree tree;

	public TreeViewComposite(Composite parent) {
		super(parent, SWT.NONE);
		// TODO Auto-generated constructor stub
		createUI();
	}

	public TreeViewComposite(Composite parent, model.Group rootGroup) {
		super(parent, SWT.NONE);
		// TODO Auto-generated constructor stub
		this.rootGroup = rootGroup;
		createUI();
		renderTree();
		// thangma
		treeSetContextMenu();
	}

	public void treeSetContextMenu() {

		Menu menu = new Menu(tree);
		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.setText("Print Element");
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				System.out.println("hello context menu");// tree.getSelection()[0].getText()
			}
		});
		tree.setMenu(menu);
	}

	public void renderTree() {
		if (rootGroup != null) {
			if(tree.getItemCount()>0)
				tree.removeAll();
			
			treeBinding(null, rootGroup, true);
		}
		
	}

	
	private void treeBinding(TreeItem rootLeaf, model.Entry entryItem,
			boolean isRoot) {
		TreeItem item = null;
		if (isRoot) {
			item = new TreeItem(tree, SWT.NONE);
		} else {
			item = new TreeItem(rootLeaf, SWT.NONE);
		}

		item.setData("DATA", entryItem);
		item.setText(entryItem.getName());
		
		
		if (entryItem instanceof model.Group) {
			for (model.Entry childItem : ((model.Group) entryItem).getEntries()) {
				treeBinding(item, childItem, false);
			}
		}

	}
	
	private void restoreTreeState(TreeItem[] listTreeNodeItem){
		if(lastSelectedData!=null){
			for (TreeItem treeItem : listTreeNodeItem) {
				model.Entry dataNode = (model.Entry)treeItem.getData("DATA");
				if(dataNode instanceof model.Group){
					if(dataNode.getID().equals(lastSelectedData.getID())){
						TreeItem parentTemp = treeItem.getParentItem();
						tree.setSelection(parentTemp);
						while(parentTemp!=null){
							parentTemp.setExpanded(true);
							parentTemp=parentTemp.getParentItem();
						}
					}else
						restoreTreeState(treeItem.getItems());
				}else{
					if(dataNode.getID().equals(lastSelectedData.getID())){
						tree.setSelection(treeItem);
						TreeItem parentTemp = treeItem.getParentItem();
						while(parentTemp!=null){
							parentTemp.setExpanded(true);
							tree.setSelection(parentTemp);
							parentTemp=parentTemp.getParentItem();
						}
					}
				}
			}
		}
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
		tree = new Tree(grp1, style);

		// add listener for tree
		tree.addTreeListener(this);
		tree.addSelectionListener(this);
		tree.pack();
		grp1.pack();

		// Layout and add group 2 to right parent container
		Group grp2 = new Group(this, SWT.NONE);
		GridData gd2 = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd2.widthHint = 400;
		// gd2.heightHint = 350;
		grp2.setLayoutData(gd2);

		GridLayout gl4group2 = new GridLayout(2, false);
		gl4group2.horizontalSpacing = 15;
		gl4group2.marginTop = 15;
		gl4group2.marginLeft = 15;
		grp2.setLayout(gl4group2);

		// layout for each element of group 2
		GridData gd3 = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd3.widthHint = 70;
		GridData gd4 = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd4.widthHint = 220;

		Label lblName = new Label(grp2, SWT.NONE);
		lblName.setText("Name");
		lblName.setLayoutData(gd3);
		lblName.pack();

		txtName = new Text(grp2, SWT.BORDER);
		txtName.setLayoutData(gd4);
		// Set listener for txtName
		txtName.addListener(SWT.FocusOut, new Listener() {

			@Override
			public void handleEvent(Event e) {
				// TODO Auto-generated method stub
				updateModel(e);
			}
		});
		// end
		txtName.pack();

		Label lblEmail = new Label(grp2, SWT.NONE);
		lblEmail.setText("Email");
		lblEmail.setLayoutData(gd3);
		lblEmail.pack();

		txtEmail = new Text(grp2, SWT.BORDER);
		txtEmail.setLayoutData(gd4);
		// Set listener for txtName
		txtEmail.addListener(SWT.FocusOut, new Listener() {

			@Override
			public void handleEvent(Event e) {
				// TODO Auto-generated method stub
				updateModel(e);
			}
		});
		// end
		txtEmail.pack();

		grp2.pack();
	}
	
	private void updateEntryByTreeNode(model.Entry entry, model.Entry dataNote){
		if(entry!=null){
			if(entry.getID().equals(dataNote.getID()))
			{
				entry = dataNote;
			}else{
				if (entry instanceof model.Group) {
					for (model.Entry childItem : ((model.Group) entry).getEntries()) {
						updateEntryByTreeNode(childItem, dataNote);
					}
				}
			}
			
		}
	}

	private void updateModel(Event e) {
		if(lastSelectedData==null)
			return;		
		if (lastSelectedData instanceof model.Group) {
			((model.Group) lastSelectedData).setName(txtName.getText());
		} else {
			((model.Person) lastSelectedData).setName(txtName.getText());
			((model.Person) lastSelectedData).setEmail(txtEmail.getText());
		}
		updateEntryByTreeNode(rootGroup, lastSelectedData);
		renderTree();
		restoreTreeState(tree.getItems());
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		TreeItem ti = (TreeItem) e.item;
		// handlerTreeNodeClick(ti.getText());
		model.Entry dataNote = (model.Entry)ti.getData("DATA");
		this.lastSelectedData = dataNote;
		if (dataNote instanceof model.Group) {
			txtName.setText(((model.Group) dataNote).getName());
			txtEmail.setText("This is a group");
		} else {
			txtName.setText(((model.Person) dataNote).getName());
			txtEmail.setText(((model.Person) dataNote).getEmail());
		}
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

}
