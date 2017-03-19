package com.gitlab.eitc_irpd.splits_happen.bowling_score;

import org.junit.Assert;
import org.junit.Test;

public class BowlingScoreCardTest {

	@Test
	public void testGetFinalScore_ManyStrikesAsPossible() {
		BowlingScoreCard card = new BowlingScoreCard("XXXXXXXXXXXX");
		Assert.assertEquals(300, card.getGameScore());
	}

	@Test
	public void testGetFinalScore_Repeat9AndMiss() {
		BowlingScoreCard card = new BowlingScoreCard("9-9-9-9-9-9-9-9-9-9-");
		Assert.assertEquals(90, card.getGameScore());
	}

	@Test
	public void testGetFinalScore_Repeat5sAndSpares() {
		BowlingScoreCard card = new BowlingScoreCard("5/5/5/5/5/5/5/5/5/5/5");
		Assert.assertEquals(150, card.getGameScore());
	}

	@Test
	public void testGetFinalScore_ComplexScoring() {
		BowlingScoreCard card = new BowlingScoreCard("X7/9-X-88/-6XXX81");
		Assert.assertEquals(167, card.getGameScore());
	}

}
