package br.com.restaurante.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.restaurante.business.UsuarioBS;
import br.com.restaurante.model.UsuarioModel;
import br.com.topsys.util.TSUtil;

@ManagedBean
public class UsuarioConverter implements Converter {

	@EJB
	private UsuarioBS usuarioBS;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String query) {

		UsuarioModel model = null;

		if (!TSUtil.isEmpty(query) && TSUtil.isNumeric(query)) {

			model = usuarioBS.obterCrudModel(new UsuarioModel(Long.valueOf(query)));

		}

		if (TSUtil.isEmpty(model)) {

			model = new UsuarioModel();

		}

		return model;

	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		UsuarioModel model = (UsuarioModel) o;
		return TSUtil.isEmpty(model.getId()) ? null : String.valueOf(model.getId());
	}

}
