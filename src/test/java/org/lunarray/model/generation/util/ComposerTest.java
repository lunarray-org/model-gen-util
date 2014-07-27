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

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.builder.annotation.simple.SimpleBuilder;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.generation.util.model.Qualifier01;
import org.lunarray.model.generation.util.model.Sample01;
import org.lunarray.model.generation.util.model.Sample02;
import org.lunarray.model.generation.util.model.SampleEnum;

/**
 * Test the composer
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class ComposerTest {

	/** The strategy builder. */
	private RenderFactory<MockContext, Sample01> builder;
	/** The composer. */
	private Composer<MockContext, Object, Sample01> composer;
	/** The context. */
	private MockContext context;
	/** The entity descriptor. */
	private EntityDescriptor<Sample01> presentationDescriptor;
	/** The model. */
	private Model<Object> presentationModel;
	/** The resolver. */
	private VariableResolver<MockContext, Object, Sample01> resolver;
	/** The entity descriptor. */
	private EntityDescriptor<Sample01> simpleDescriptor;
	/** The model. */
	private Model<Object> simpleModel;

	/** Sets up the test. */
	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws Exception {
		this.resolver = EasyMock.createMock(VariableResolver.class);
		// TODO create not nice mock
		this.builder = EasyMock.createNiceMock(RenderFactory.class);
		this.context = new MockContext();
		this.composer = new Composer<MockContext, Object, Sample01>();
		final SimpleClazzResource<Object> resource = new SimpleClazzResource<Object>(Sample01.class, Sample02.class, SampleEnum.class);
		this.presentationModel = PresQualBuilder.createBuilder().resources(resource).build();
		this.simpleModel = SimpleBuilder.createBuilder().resources(resource).build();
		this.presentationDescriptor = this.presentationModel.getEntity(Sample01.class);
		this.simpleDescriptor = this.simpleModel.getEntity(Sample01.class);
		this.composer.setContext(this.context);
		this.composer.setPropertyRenderStrategyFactory(this.builder);
		this.composer.setVariableResolver(this.resolver);
		EasyMock.reset(this.resolver, this.builder);
	}

	/**
	 * Test the composer.
	 * 
	 * @see Composer#compose(boolean)
	 */
	@Test
	public void testPresentationNoQualifierComposition() {
		EasyMock.expect(this.resolver.hasQualifier(this.context)).andReturn(false).anyTimes();
		EasyMock.expect(this.resolver.getDescriptor(this.context)).andReturn(this.presentationDescriptor).anyTimes();
		EasyMock.expect(this.resolver.getModel(this.context)).andReturn(this.presentationModel).anyTimes();
		EasyMock.expect(this.resolver.getLocale(this.context)).andReturn(Locale.getDefault()).anyTimes();
		EasyMock.replay(this.resolver, this.builder);
		this.composer.compose(false);
		Assert.assertEquals("Sample object 01", this.composer.getLabel());
		EasyMock.verify(this.resolver, this.builder);
	}

	/**
	 * Test the composer.
	 * 
	 * @see Composer#compose(boolean)
	 */
	@Test
	public void testPresentationNoQualifierCompositionOperations() {
		EasyMock.expect(this.resolver.hasQualifier(this.context)).andReturn(false).anyTimes();
		EasyMock.expect(this.resolver.getDescriptor(this.context)).andReturn(this.presentationDescriptor).anyTimes();
		EasyMock.expect(this.resolver.getModel(this.context)).andReturn(this.presentationModel).anyTimes();
		EasyMock.expect(this.resolver.getLocale(this.context)).andReturn(Locale.getDefault()).anyTimes();
		EasyMock.replay(this.resolver, this.builder);
		this.composer.compose(true);
		Assert.assertEquals("Sample object 01", this.composer.getLabel());
		EasyMock.verify(this.resolver, this.builder);
	}

	/**
	 * Test the composer.
	 * 
	 * @see Composer#compose(boolean)
	 */
	@Test
	public void testPresentationQualifierComposition() {
		EasyMock.expect(this.resolver.hasQualifier(this.context)).andReturn(true).anyTimes();
		EasyMock.expect(this.resolver.getDescriptor(this.context)).andReturn(this.presentationDescriptor).anyTimes();
		EasyMock.expect(this.resolver.getModel(this.context)).andReturn(this.presentationModel).anyTimes();
		EasyMock.expect(this.resolver.getLocale(this.context)).andReturn(Locale.getDefault()).anyTimes();
		final Class<?> qualifier = Qualifier01.class;
		this.resolver.getQualifier(this.context);
		EasyMock.expectLastCall().andReturn(qualifier).anyTimes();
		EasyMock.replay(this.resolver, this.builder);
		this.composer.compose(false);
		Assert.assertEquals("Sample object 01", this.composer.getLabel());
		EasyMock.verify(this.resolver, this.builder);
	}

	/**
	 * Test the composer.
	 * 
	 * @see Composer#compose(boolean)
	 */
	@Test
	public void testPresentationQualifierCompositionOperations() {
		EasyMock.expect(this.resolver.hasQualifier(this.context)).andReturn(true).anyTimes();
		EasyMock.expect(this.resolver.getDescriptor(this.context)).andReturn(this.presentationDescriptor).anyTimes();
		EasyMock.expect(this.resolver.getModel(this.context)).andReturn(this.presentationModel).anyTimes();
		EasyMock.expect(this.resolver.getLocale(this.context)).andReturn(Locale.getDefault()).anyTimes();
		final Class<?> qualifier = Qualifier01.class;
		this.resolver.getQualifier(this.context);
		EasyMock.expectLastCall().andReturn(qualifier).anyTimes();
		EasyMock.replay(this.resolver, this.builder);
		this.composer.compose(true);
		Assert.assertEquals("Sample object 01", this.composer.getLabel());
		EasyMock.verify(this.resolver, this.builder);
	}

	/**
	 * Test the composer.
	 * 
	 * @see Composer#compose(boolean)
	 */
	@Test
	public void testSimpleNoQualifierComposition() {
		EasyMock.expect(this.resolver.hasQualifier(this.context)).andReturn(false).anyTimes();
		EasyMock.expect(this.resolver.getDescriptor(this.context)).andReturn(this.simpleDescriptor).anyTimes();
		EasyMock.expect(this.resolver.getModel(this.context)).andReturn(this.simpleModel).anyTimes();
		EasyMock.expect(this.resolver.getLocale(this.context)).andReturn(Locale.getDefault()).anyTimes();
		EasyMock.replay(this.resolver, this.builder);
		this.composer.compose(false);
		Assert.assertEquals("Sample01", this.composer.getLabel());
		EasyMock.verify(this.resolver, this.builder);
	}

	/**
	 * Test the composer.
	 * 
	 * @see Composer#compose(boolean)
	 */
	@Test
	public void testSimpleNoQualifierCompositionOperations() {
		EasyMock.expect(this.resolver.hasQualifier(this.context)).andReturn(false).anyTimes();
		EasyMock.expect(this.resolver.getDescriptor(this.context)).andReturn(this.simpleDescriptor).anyTimes();
		EasyMock.expect(this.resolver.getModel(this.context)).andReturn(this.simpleModel).anyTimes();
		EasyMock.expect(this.resolver.getLocale(this.context)).andReturn(Locale.getDefault()).anyTimes();
		EasyMock.replay(this.resolver, this.builder);
		this.composer.compose(true);
		Assert.assertEquals("Sample01", this.composer.getLabel());
		EasyMock.verify(this.resolver, this.builder);
	}

	/**
	 * Test the composer.
	 * 
	 * @see Composer#compose(boolean)
	 */
	@Test
	public void testSimpleQualifierComposition() {
		EasyMock.expect(this.resolver.hasQualifier(this.context)).andReturn(true).anyTimes();
		EasyMock.expect(this.resolver.getDescriptor(this.context)).andReturn(this.simpleDescriptor).anyTimes();
		EasyMock.expect(this.resolver.getModel(this.context)).andReturn(this.simpleModel).anyTimes();
		EasyMock.expect(this.resolver.getLocale(this.context)).andReturn(Locale.getDefault()).anyTimes();
		final Class<?> qualifier = Qualifier01.class;
		this.resolver.getQualifier(this.context);
		EasyMock.expectLastCall().andReturn(qualifier).anyTimes();
		EasyMock.replay(this.resolver, this.builder);
		this.composer.compose(false);
		Assert.assertEquals("Sample01", this.composer.getLabel());
		EasyMock.verify(this.resolver, this.builder);
	}

	/**
	 * Test the composer.
	 * 
	 * @see Composer#compose(boolean)
	 */
	@Test
	public void testSimpleQualifierCompositionOperations() {
		EasyMock.expect(this.resolver.hasQualifier(this.context)).andReturn(true).anyTimes();
		EasyMock.expect(this.resolver.getDescriptor(this.context)).andReturn(this.simpleDescriptor).anyTimes();
		EasyMock.expect(this.resolver.getModel(this.context)).andReturn(this.simpleModel).anyTimes();
		EasyMock.expect(this.resolver.getLocale(this.context)).andReturn(Locale.getDefault()).anyTimes();
		final Class<?> qualifier = Qualifier01.class;
		this.resolver.getQualifier(this.context);
		EasyMock.expectLastCall().andReturn(qualifier).anyTimes();
		EasyMock.replay(this.resolver, this.builder);
		this.composer.compose(true);
		Assert.assertEquals("Sample01", this.composer.getLabel());
		EasyMock.verify(this.resolver, this.builder);
	}
}
