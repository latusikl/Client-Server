package pl.polsl.lukasz.latusik.lab.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 The type Test suite.
 @author Łukasz Latuisk
 @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
		ClientTest.class,
		FileManagerTest.class,
		ProductCategoryTest.class,
		ProductTest.class,
		ShopTest.class,
})
public class TestSuite {
}
