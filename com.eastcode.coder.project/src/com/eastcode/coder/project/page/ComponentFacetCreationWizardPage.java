package com.eastcode.coder.project.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jst.j2ee.project.facet.IJ2EEFacetProjectCreationDataModelProperties;
import org.eclipse.jst.j2ee.project.facet.IJ2EEModuleFacetInstallDataModelProperties;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetProjectCreationDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.web.ui.internal.wizards.DataModelFacetCreationWizardPage;

public abstract class ComponentFacetCreationWizardPage extends DataModelFacetCreationWizardPage implements
		IFacetProjectCreationDataModelProperties {

	private static final String STORE_LABEL = "LASTEARNAME_"; //$NON-NLS-1$

	public ComponentFacetCreationWizardPage(IDataModel dataModel, String pageName) {
		super(dataModel, pageName);
	}

	protected Composite createTopLevelComposite(Composite parent) {
		final Composite top = super.createTopLevelComposite(parent);
		return top;
	}

	protected abstract String getModuleFacetID();

	protected String getModuleTypeID() {
		return getModuleFacetID();
	}

	public void dispose() {
		super.dispose();
	}

	public void storeDefaultSettings() {
		super.storeDefaultSettings();
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			FacetDataModelMap map = (FacetDataModelMap) model
					.getProperty(IFacetProjectCreationDataModelProperties.FACET_DM_MAP);
			String facetID = getModuleFacetID();
			IDataModel j2eeModel = map.getFacetDataModel(facetID);
			if (j2eeModel.getBooleanProperty(IJ2EEModuleFacetInstallDataModelProperties.ADD_TO_EAR)) {
				String lastEARName = j2eeModel
						.getStringProperty(IJ2EEModuleFacetInstallDataModelProperties.EAR_PROJECT_NAME);
				settings.put(STORE_LABEL, lastEARName);
			}
		}
	}

	public void restoreDefaultSettings() {
		super.restoreDefaultSettings();
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String lastEARName = settings.get(STORE_LABEL);
			if (lastEARName != null) {
				FacetDataModelMap map = (FacetDataModelMap) model
						.getProperty(IFacetProjectCreationDataModelProperties.FACET_DM_MAP);
				String facetID = getModuleFacetID();
				IDataModel j2eeModel = map.getFacetDataModel(facetID);
				j2eeModel.setProperty(IJ2EEModuleFacetInstallDataModelProperties.LAST_EAR_NAME, lastEARName);
			}
		}
	}

	protected String[] getValidationPropertyNames() {
		String[] superProperties = super.getValidationPropertyNames();
		List<String> list = Arrays.asList(superProperties);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.addAll(list);
		arrayList.add(IJ2EEFacetProjectCreationDataModelProperties.EAR_PROJECT_NAME);
		arrayList.add(IJ2EEFacetProjectCreationDataModelProperties.ADD_TO_EAR);
		return (String[]) arrayList.toArray(new String[0]);
	}
}
