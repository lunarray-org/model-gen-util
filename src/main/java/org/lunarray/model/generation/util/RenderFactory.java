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

import java.util.Collection;

import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.operation.parameters.CollectionParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.result.CollectionResultDescriptor;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.model.property.CollectionPropertyDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;

/**
 * The factory for composing the rendering.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The context type.
 * @param <E>
 *            The entity type.
 */
public interface RenderFactory<C extends Context, E> {

	/**
	 * Begin a operation.
	 * 
	 * @param context
	 *            The context.
	 * @param descriptor
	 *            The descriptor.
	 */
	void beginOperation(C context, OperationDescriptor<E> descriptor);

	/**
	 * End a operation.
	 * 
	 * @param context
	 *            The context.
	 * @param operation
	 *            The operation.
	 */
	void endOperation(C context, OperationDescriptor<E> operation);

	/**
	 * Render a collection parameter.
	 * 
	 * @param context
	 *            The context.
	 * @param operation
	 *            The operation.
	 * @param descriptor
	 *            The descriptor.
	 * @param renderType
	 *            The render type.
	 * @param <D>
	 *            The collection type.
	 * @param <P>
	 *            The parameter type.
	 */
	<D, P extends Collection<D>> void renderCollectionParameter(C context, OperationDescriptor<E> operation,
			CollectionParameterDescriptor<D, P> descriptor, RenderType renderType);

	/**
	 * Render a collection property.
	 * 
	 * @param context
	 *            The context.
	 * @param descriptor
	 *            The descriptor.
	 * @param renderType
	 *            The render type.
	 * @param <D>
	 *            The collection type.
	 * @param <P>
	 *            The property type.
	 */
	<D, P extends Collection<D>> void renderCollectionProperty(C context, CollectionPropertyDescriptor<D, P, E> descriptor,
			RenderType renderType);

	/**
	 * Render a collection result type.
	 * 
	 * @param context
	 *            The context.
	 * @param operation
	 *            The operation.
	 * @param resultDescriptor
	 *            The result descriptor.
	 * @param renderType
	 *            The render type.
	 * @param <D>
	 *            The collection type.
	 * @param <R>
	 *            The result type.
	 */
	<D, R extends Collection<D>> void renderCollectionResultType(C context, OperationDescriptor<E> operation,
			CollectionResultDescriptor<D, R> resultDescriptor, RenderType renderType);

	/**
	 * Render a parameter.
	 * 
	 * @param context
	 *            The context.
	 * @param descriptor
	 *            The descriptor.
	 * @param operation
	 *            The operation.
	 * @param renderType
	 *            The render type.
	 * @param <P>
	 *            The parameter type.
	 */
	<P> void renderParameter(C context, ParameterDescriptor<P> descriptor, OperationDescriptor<E> operation, RenderType renderType);

	/**
	 * Render property.
	 * 
	 * @param context
	 *            The context.
	 * @param descriptor
	 *            The descriptor.
	 * @param renderType
	 *            The render type.
	 * @param <P>
	 *            The property type.
	 */
	<P> void renderProperty(C context, PropertyDescriptor<P, E> descriptor, RenderType renderType);

	/**
	 * Render a result type.
	 * 
	 * @param context
	 *            The context.
	 * @param operation
	 *            The operation.
	 * @param resultDescriptor
	 *            The result type.
	 * @param renderType
	 *            The render type.
	 * @param <R>
	 *            The result type.
	 */
	<R> void renderResultType(C context, OperationDescriptor<E> operation, ResultDescriptor<R> resultDescriptor, RenderType renderType);
}
