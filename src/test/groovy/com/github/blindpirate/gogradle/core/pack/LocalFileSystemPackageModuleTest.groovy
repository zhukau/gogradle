package com.github.blindpirate.gogradle.core.pack

import com.github.blindpirate.gogradle.GogradleRunner
import com.github.blindpirate.gogradle.WithResource
import com.github.blindpirate.gogradle.core.dependency.DependencyHelper
import com.github.blindpirate.gogradle.core.dependency.resolve.DependencyFactory
import com.github.blindpirate.gogradle.core.dependency.resolve.VendorDependencyFactory
import com.google.inject.Injector
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(GogradleRunner)
@WithResource('vendor_test.zip')
class LocalFileSystemPackageModuleTest {

    File resource

    @Mock
    Injector injector


    @Before
    void setUp() {
        DependencyHelper.INJECTOR_INSTANCE = injector
        Mockito.when(injector.getInstance(DependencyFactory.class)).thenReturn(new VendorDependencyFactory())
    }

    @Test
    void 'create cascading vendor package should success'() {
        LocalFileSystemModule module = LocalFileSystemModule.fromFileSystem('testpackage', resource);

        assert module.dependencies.any {
            it.package.name == 'github.com/e/f'
        }
        assert module.dependencies.any {
            it.package.name == 'github.com/e/g'
        }

        def ef = module.dependencies.find { it.package.name == 'github.com/e/f' }
        assert ef.package.dependencies.any { it.package.name == 'github.com/j/k' }

    }
}
