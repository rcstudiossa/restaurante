/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.restaurante.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.restaurante.business.FuncaoBS;
import br.com.restaurante.model.FuncaoModel;
import br.com.topsys.util.TSUtil;

/**
 *
 * @author Pc
 */

@ManagedBean
public class FuncaoConverter implements Converter {

	@EJB
	private FuncaoBS funcaoBS;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String query) {

		FuncaoModel model = null;

		if (!TSUtil.isEmpty(query) && TSUtil.isNumeric(query)) {

			model = funcaoBS.obterCrudModel(new FuncaoModel(Long.valueOf(query)));

		}

		if (TSUtil.isEmpty(model)) {

			model = new FuncaoModel();

		}

		return model;

	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		FuncaoModel model = (FuncaoModel) o;
		return TSUtil.isEmpty(model.getId()) ? null : String.valueOf(model.getId());
	}

}
