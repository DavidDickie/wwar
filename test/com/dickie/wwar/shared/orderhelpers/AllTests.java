package com.dickie.wwar.shared.orderhelpers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
//@SuiteClasses({ MyClassTest.class, MySecondClassTest.class })
@RunWith(Suite.class)
@SuiteClasses({ArmorHelperTest.class, BlockPathHelperTest.class,BuySpellHelperTest.class,
	CreateGolumHelperTest.class, DamageHelperTest.class, DiscardHelperTest.class,
	DrawHelperTest.class, FamilliarHelperTest.class, HealHelperTest.class})
public class AllTests {

}
