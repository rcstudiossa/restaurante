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

import br.com.restaurante.business.ProcedimentoBS;
import br.com.restaurante.model.ProcedimentoModel;
import br.com.topsys.util.TSUtil;

@ManagedBean
public class ProcedimentoConverter implements Converter {

	@EJB
	private ProcedimentoBS procedimentoBS;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String query) {

		if (!TSUtil.isEmpty(query) && TSUtil.isNumeric(query)) {

			return this.procedimentoBS.obterCrudModel(new ProcedimentoModel(Long.valueOf(query)));

		}

		return new ProcedimentoModel();
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		return String.valueOf(((ProcedimentoModel) o).getId());
	}

}
