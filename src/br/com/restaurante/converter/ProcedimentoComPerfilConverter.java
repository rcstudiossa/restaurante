/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.restaurante.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.restaurante.model.ProcedimentoModel;
import br.com.topsys.util.TSUtil;

@ManagedBean
public class ProcedimentoComPerfilConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String query) {

		if (!TSUtil.isEmpty(query) && query.contains(":")) {
			
			String[] split = query.split(":");
			
			if(split.length > 0 && TSUtil.isNumeric(split[1])){
				
				ProcedimentoModel procedimentoModel = new ProcedimentoModel();
				
				procedimentoModel.setId(Long.valueOf(split[1]));
				procedimentoModel.setTipo(split[0]);
				
				return procedimentoModel;
				
			}
			
		}

		return new ProcedimentoModel();
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		return String.valueOf(((ProcedimentoModel) o).getTipo() + ":" + ((ProcedimentoModel) o).getId());
	}

}
