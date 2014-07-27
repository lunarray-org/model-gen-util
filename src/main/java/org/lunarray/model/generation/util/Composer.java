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
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.member.MemberDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.operation.parameters.CollectionParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.result.CollectionResultDescriptor;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.model.property.CollectionPropertyDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationEntityDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationParameterDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationPropertyDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationResultDescriptor;
import org.lunarray.model.descriptor.presentation.RelationPresentationDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.qualifier.QualifierEntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The render composer. Composes the rendering.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The context type.
 * @param <S>
 *            The super type.
 * @param <E>
 *            The entity type.
 */
public final class Composer<C extends Context, S, E extends S> {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(Composer.class);
	/** The context. */
	private transient C context;
	/** The row strategy factory. */
	private transient RenderFactory<C, E> strategyFactory;
	/** A variable resolver. */
	private transient VariableResolver<C, S, E> variableResolver;

	/**
	 * Default constructor.
	 */
	public Composer() {
		// Default constructor.
	}

	/**
	 * Composes the property render strategy list.
	 * 
	 * @param includeOperations
	 *            Include the operations.
	 */
	public void compose(final boolean includeOperations) {
		Composer.LOGGER.debug("Started composition, inculde operations: {}", includeOperations);
		// Get the descriptor.
		final EntityDescriptor<E> entityDescriptor = this.getEntityDescriptor();
		// Get presentation descriptor.
		@SuppressWarnings("unchecked")
		// Can't be more sure.
		final PresentationEntityDescriptor<E> presentationDescriptor = entityDescriptor.adapt(PresentationEntityDescriptor.class);
		// Get strategies.
		List<? extends MemberDescriptor<E>> memberDescriptors;
		if (CheckUtil.isNull(presentationDescriptor)) {
			Composer.LOGGER.debug("Processing non-presentation descriptor.");
			if (includeOperations) {
				memberDescriptors = new LinkedList<MemberDescriptor<E>>(entityDescriptor.getMembers());
			} else {
				memberDescriptors = new LinkedList<MemberDescriptor<E>>(entityDescriptor.getProperties());
			}
		} else {
			Composer.LOGGER.debug("Processing presentation descriptor.");
			if (includeOperations) {
				memberDescriptors = presentationDescriptor.getOrderedMembers();
			} else {
				memberDescriptors = presentationDescriptor.getOrderedProperties();
			}
		}
		final Class<?> qualifier = this.getQualifier();
		Composer.LOGGER.debug("Resolving {} members for qualifier '{}'.", memberDescriptors.size(), qualifier);
		this.resolveMembers(memberDescriptors, qualifier);
	}

	/**
	 * Gets the value for the context field.
	 * 
	 * @return The value for the context field.
	 */
	public C getContext() {
		return this.context;
	}

	/**
	 * Gets the label for the form.
	 * 
	 * @return The label.
	 */
	public String getLabel() {
		// Get the descriptor.
		final EntityDescriptor<?> entityDescriptor = this.getEntityDescriptor();
		// Get presentation descriptor.
		final PresentationEntityDescriptor<?> presentationDescriptor = entityDescriptor.adapt(PresentationEntityDescriptor.class);
		// The label.
		String label;
		Locale locale = null;
		if (CheckUtil.isNull(presentationDescriptor)) {
			label = entityDescriptor.getName();
		} else {
			locale = this.variableResolver.getLocale(this.context);
			if (CheckUtil.isNull(locale)) {
				label = presentationDescriptor.getDescription();
			} else {
				label = presentationDescriptor.getDescription(locale);
			}
		}
		Composer.LOGGER.debug("Resolved label '{}' for locale '{}'.", label, locale);
		return label;
	}

	/**
	 * Gets the value for the propertyRenderStrategyFactory field.
	 * 
	 * @return The value for the propertyRenderStrategyFactory field.
	 */
	public RenderFactory<C, E> getPropertyRenderStrategyFactory() {
		return this.strategyFactory;
	}

	/**
	 * Gets the value for the variableResolver field.
	 * 
	 * @return The value for the variableResolver field.
	 */
	public VariableResolver<C, S, E> getVariableResolver() {
		return this.variableResolver;
	}

	/**
	 * Sets a new value for the context field.
	 * 
	 * @param context
	 *            The new value for the context field.
	 */
	public void setContext(final C context) {
		this.context = context;
	}

	/**
	 * Sets a new value for the propertyRenderStrategyFactory field.
	 * 
	 * @param strategyFactory
	 *            The new value for the propertyRenderStrategyFactory field.
	 */
	public void setPropertyRenderStrategyFactory(final RenderFactory<C, E> strategyFactory) {
		this.strategyFactory = strategyFactory;
	}

	/**
	 * Sets a new value for the variableResolver field.
	 * 
	 * @param variableResolver
	 *            The new value for the variableResolver field.
	 */
	public void setVariableResolver(final VariableResolver<C, S, E> variableResolver) {
		this.variableResolver = variableResolver;
	}

	/**
	 * Make a list from a collection.
	 * 
	 * @param <T>
	 *            The collection type.
	 * @param collection
	 *            The collection.
	 * @return The list.
	 */
	private <T> List<T> collectionToList(final Collection<T> collection) {
		return new LinkedList<T>(collection);
	}

	/**
	 * Gets the entity descriptor.
	 * 
	 * @return The entity descriptor.
	 */
	private EntityDescriptor<E> getEntityDescriptor() {
		final Class<?> qualifier = this.getQualifier();
		EntityDescriptor<E> entityDescriptor;
		entityDescriptor = this.variableResolver.getDescriptor(this.context);
		@SuppressWarnings("unchecked")
		// Can't be more sure.
		final QualifierEntityDescriptor<E> qualifierDescriptor = entityDescriptor.adapt(QualifierEntityDescriptor.class);
		if (!CheckUtil.isNull(qualifier) && !CheckUtil.isNull(qualifierDescriptor)) {
			entityDescriptor = qualifierDescriptor.getQualifierEntity(qualifier);
		}
		Composer.LOGGER.debug("Resolved entity descriptor: {}", entityDescriptor);
		return entityDescriptor;
	}

	/**
	 * Gets the qualifier.
	 * 
	 * @return The qualifier.
	 */
	private Class<?> getQualifier() {
		Class<?> qualifier = null;
		// Find qualifier.
		if (this.variableResolver.hasQualifier(this.context)) {
			qualifier = this.variableResolver.getQualifier(this.context);
		}
		return qualifier;
	}

	/**
	 * Process an inline property.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @param property
	 *            The property.
	 * @param innerDescriptorArgument
	 *            The descriptor.
	 * @param <F>
	 *            The entity type.
	 */
	private <F extends S> void processInline(final Class<?> qualifier, final PropertyDescriptor<F, ?> property,
			final EntityDescriptor<F> innerDescriptorArgument) {
		Composer.LOGGER.debug("Processing inline descriptor '{}' for property: {}", innerDescriptorArgument, property);
		// Get qualifier descriptor.
		EntityDescriptor<F> innerDescriptor = innerDescriptorArgument;
		@SuppressWarnings("unchecked")
		// Can't be more sure.
		final QualifierEntityDescriptor<F> innerQualifierDescriptor = innerDescriptor.adapt(QualifierEntityDescriptor.class);
		if (!CheckUtil.isNull(qualifier) && !CheckUtil.isNull(innerQualifierDescriptor)) {
			innerDescriptor = innerQualifierDescriptor.getQualifierEntity(qualifier);
		}
		// Presentation descriptor.
		@SuppressWarnings("unchecked")
		// Can't be more sure.
		final PresentationEntityDescriptor<F> innerPresentationDescriptor = innerDescriptor.adapt(PresentationEntityDescriptor.class);
		// Resolve properties.
		List<MemberDescriptor<F>> innerProperties;
		if (CheckUtil.isNull(innerPresentationDescriptor)) {
			innerProperties = this.collectionToList(innerDescriptor.getMembers());
		} else {
			innerProperties = innerPresentationDescriptor.getOrderedMembers();
		}
		Composer.LOGGER.debug("Resolved {} properties for inner descriptor: {}", innerProperties.size(), innerDescriptorArgument);
		// Push variable.
		this.context.pushPrefix(property);
		// Render properties.
		this.resolveMembers(innerProperties, qualifier);
		// Pop variable.
		this.context.popPrefix();
	}

	/**
	 * Process presentation descriptor.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @param presentationParameter
	 *            The presentation parameter.
	 * @param operation
	 *            The operation.
	 * @param <P>
	 *            The parameter type.
	 * @param <D>
	 *            The parameter Collection type.
	 */
	@SuppressWarnings("unchecked")
	private <P, D extends Collection<P>> void processPresentationDescriptor(final ParameterDescriptor<P> parameter,
			final PresentationParameterDescriptor<P> presentationParameter, final OperationDescriptor<E> operation) {
		final CollectionParameterDescriptor<P, D> descriptor = parameter.adapt(CollectionParameterDescriptor.class);
		if (CheckUtil.isNull(presentationParameter)) {
			this.processPresentationParameter(parameter, operation, descriptor);
		} else {
			if (CheckUtil.isNull(descriptor)) {
				this.strategyFactory.renderParameter(this.context, presentationParameter, operation, presentationParameter.getRenderType());
			} else {
				this.strategyFactory.renderCollectionParameter(this.context, operation, descriptor, presentationParameter.getRenderType());
			}
		}
	}

	/**
	 * Process presentation descriptor.
	 * 
	 * @param property
	 *            The property.
	 * @param presentationProperty
	 *            The descriptor.
	 * @param <P>
	 *            The property type.
	 * @param <F>
	 *            The entity type.
	 */
	@SuppressWarnings("unchecked")
	private <P, F> void processPresentationDescriptor(final PropertyDescriptor<P, F> property,
			final PresentationPropertyDescriptor<P, F> presentationProperty) {
		final CollectionPropertyDescriptor<P, Collection<P>, F> descriptor = property.adapt(CollectionPropertyDescriptor.class);
		if (CheckUtil.isNull(presentationProperty)) {
			this.processPresentationProperty(property, descriptor);
		} else {
			if (CheckUtil.isNull(descriptor)) {
				this.strategyFactory.renderProperty(this.context, (PropertyDescriptor<P, E>) presentationProperty,
						presentationProperty.getRenderType());
			} else {
				this.strategyFactory.renderCollectionProperty(this.context, (CollectionPropertyDescriptor<P, Collection<P>, E>) descriptor,
						presentationProperty.getRenderType());
			}
		}
	}

	/**
	 * Process the presentation descriptor.
	 * 
	 * @param parameter
	 *            The result type.
	 * @param presentationParameter
	 *            The presentation result type.
	 * @param operation
	 *            The operation.
	 * @param <R>
	 *            The result type.
	 * @param <D>
	 *            The parameter Collection type.
	 */
	@SuppressWarnings("unchecked")
	private <R, D extends Collection<R>> void processPresentationDescriptor(final ResultDescriptor<R> parameter,
			final PresentationResultDescriptor<R> presentationParameter, final OperationDescriptor<E> operation) {
		final CollectionResultDescriptor<R, D> descriptor = parameter.adapt(CollectionResultDescriptor.class);
		if (CheckUtil.isNull(presentationParameter)) {
			this.processPresentationResult(parameter, operation, descriptor);
		} else {
			if (CheckUtil.isNull(descriptor)) {
				this.strategyFactory
						.renderResultType(this.context, operation, presentationParameter, presentationParameter.getRenderType());
			} else {
				this.strategyFactory.renderCollectionResultType(this.context, operation, descriptor, presentationParameter.getRenderType());
			}
		}
	}

	/**
	 * Process presentation descriptor.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @param operation
	 *            The operation.
	 * @param descriptor
	 *            The collection descriptor.
	 * @param <P>
	 *            The parameter type.
	 * @param <D>
	 *            The parameter Collection type.
	 */
	private <P, D extends Collection<P>> void processPresentationParameter(final ParameterDescriptor<P> parameter,
			final OperationDescriptor<E> operation, final CollectionParameterDescriptor<P, D> descriptor) {
		if (parameter.isRelation()) {
			if (CheckUtil.isNull(descriptor)) {
				this.strategyFactory.renderParameter(this.context, parameter, operation, RenderType.PICKLIST);
			} else {
				this.strategyFactory.renderCollectionParameter(this.context, operation, descriptor, RenderType.DROPDOWN);
			}
		} else {
			this.strategyFactory.renderParameter(this.context, parameter, operation, RenderType.TEXT);
		}
	}

	/**
	 * Process presentation property.
	 * 
	 * @param property
	 *            The property.
	 * @param descriptor
	 *            The descriptor.
	 * @param <P>
	 *            The property type.
	 * @param <F>
	 *            The entity type.
	 */
	@SuppressWarnings("unchecked")
	private <P, F> void processPresentationProperty(final PropertyDescriptor<P, F> property,
			final CollectionPropertyDescriptor<P, Collection<P>, F> descriptor) {
		if (property.isRelation()) {
			if (CheckUtil.isNull(descriptor)) {
				this.strategyFactory.renderProperty(this.context, (PropertyDescriptor<P, E>) property, RenderType.PICKLIST);
			} else {
				this.strategyFactory.renderCollectionProperty(this.context, (CollectionPropertyDescriptor<P, Collection<P>, E>) descriptor,
						RenderType.DROPDOWN);
			}
		} else {
			this.strategyFactory.renderProperty(this.context, (PropertyDescriptor<P, E>) property, RenderType.TEXT);
		}
	}

	/**
	 * Process a presentation result type.
	 * 
	 * @param parameter
	 *            The result type.
	 * @param operation
	 *            The operation.
	 * @param descriptor
	 *            The descriptor.
	 * @param <R>
	 *            The result type.
	 * @param <D>
	 *            The collection type.
	 */
	private <R, D extends Collection<R>> void processPresentationResult(final ResultDescriptor<R> parameter,
			final OperationDescriptor<E> operation, final CollectionResultDescriptor<R, D> descriptor) {
		if (parameter.isRelation()) {
			if (CheckUtil.isNull(descriptor)) {
				this.strategyFactory.renderResultType(this.context, operation, parameter, RenderType.PICKLIST);
			} else {
				this.strategyFactory.renderCollectionResultType(this.context, operation, descriptor, RenderType.DROPDOWN);
			}
		} else {
			this.strategyFactory.renderResultType(this.context, operation, parameter, RenderType.TEXT);
		}
	}

	/**
	 * Renders a property.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @param property
	 *            The property.
	 * @param <P>
	 *            The property type.
	 * @param <G>
	 *            The entity type.
	 * @param <F>
	 *            The entity type.
	 */
	@SuppressWarnings("unchecked")
	// We are fairly sure.
	private <P, G, F extends S> void renderProperty(final Class<?> qualifier, final PropertyDescriptor<P, G> property) {
		// Push variable.
		this.context.pushPrefix(property);
		final PresentationPropertyDescriptor<P, G> presentationProperty = property.adapt(PresentationPropertyDescriptor.class);
		boolean visible;
		if (CheckUtil.isNull(presentationProperty)) {
			visible = true;
		} else {
			visible = presentationProperty.isVisible();
		}
		Composer.LOGGER.debug("Property is visible {}: {}", visible, property);
		if (visible) {
			if (property.isImmutable()) {
				this.strategyFactory.renderProperty(this.context, (PropertyDescriptor<P, E>) property, RenderType.UNDEFINED);
			} else {
				this.processPresentationDescriptor(property, presentationProperty);
			}
		}
		final RelationPresentationDescriptor relationPresentation = property.adapt(RelationPresentationDescriptor.class);
		final PropertyDescriptor<F, E> relationDescriptor = (PropertyDescriptor<F, E>) property;
		if (!CheckUtil.isNull(relationPresentation) && relationPresentation.isInLineIndication()) {
			final EntityDescriptor<F> innerDescriptor = this.variableResolver.getModel(this.context).getEntity(
					relationDescriptor.getPropertyType());
			if (CheckUtil.isNull(innerDescriptor)) {
				Composer.LOGGER.warn("Could not process inner descriptor '{}'.", relationPresentation.getRelatedName());
			} else {
				this.processInline(qualifier, relationDescriptor, innerDescriptor);
			}
		}
		// Pop variable.
		this.context.popPrefix();
	}

	/**
	 * Resolve property list.
	 * 
	 * @param members
	 *            The property list.
	 * @param qualifier
	 *            The qualifier.
	 * @param <G>
	 *            The entity type.
	 */
	@SuppressWarnings("unchecked")
	private <G extends S> void resolveMembers(final List<? extends MemberDescriptor<G>> members, final Class<?> qualifier) {
		for (final MemberDescriptor<G> member : members) {
			if (member instanceof PropertyDescriptor) {
				this.renderProperty(qualifier, (PropertyDescriptor<?, G>) member);
			} else if (member instanceof OperationDescriptor) {
				final OperationDescriptor<E> operationDescriptor = (OperationDescriptor<E>) member;
				this.strategyFactory.beginOperation(this.getContext(), operationDescriptor);
				for (final ParameterDescriptor<?> parameter : operationDescriptor.getParameters()) {
					this.processPresentationDescriptor(parameter, parameter.adapt(PresentationParameterDescriptor.class),
							operationDescriptor);
				}
				final ResultDescriptor<?> resultDescriptor = operationDescriptor.getResultDescriptor();
				if (!CheckUtil.isNull(resultDescriptor)) {
					this.processPresentationDescriptor(resultDescriptor, resultDescriptor.adapt(PresentationResultDescriptor.class),
							operationDescriptor);
				}
				this.strategyFactory.endOperation(this.getContext(), operationDescriptor);
			}
		}
	}
}
