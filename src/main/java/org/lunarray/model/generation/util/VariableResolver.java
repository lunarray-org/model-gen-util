/* 
 * Model Tools.
 * Copyright (C) 2013 Pal Hargitai (pal@lunarray.org)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lunarray.model.generation.util;

import java.util.Locale;

import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;

/**
 * The variable resolver used by the {@link Composer} to expose the external
 * context.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The context type.
 * @param <S>
 *            The super type.
 * @param <E>
 *            The entity type.
 */
public interface VariableResolver<C extends Context, S, E extends S> {

	/**
	 * Gets the descriptor.
	 * 
	 * @param context
	 *            The context.
	 * @return The descriptor.
	 */
	EntityDescriptor<E> getDescriptor(C context);

	/**
	 * Gets the locale.
	 * 
	 * @param context
	 *            The context.
	 * @return The locale.
	 */
	Locale getLocale(C context);

	/**
	 * Gets the model.
	 * 
	 * @param context
	 *            The context.
	 * @return The model.
	 */
	Model<S> getModel(C context);

	/**
	 * Gets the qualifier.
	 * 
	 * @param context
	 *            The context.
	 * @return The qualifier.
	 */
	Class<?> getQualifier(C context);

	/**
	 * Tests if there is a qualifier.
	 * 
	 * @param context
	 *            The context.
	 * @return True if there is a qualifier, false otherwise.
	 */
	boolean hasQualifier(C context);
}
