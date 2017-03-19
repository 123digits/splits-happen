package com.gitlab.eitc_irpd.splits_happen.bowling_score;

import java.util.stream.IntStream;

/** Assuming given a valid sequence of rolls for one line of American Ten-Pin Ten-Frame Bowling, this class produces the
 * total score for the game. Run Configuration -> Arguments -> Program Arguments -> XXXXXXXXXXXX 9-9-9-9-9-9-9-9-9-9-
 * 5/5/5/5/5/5/5/5/5/5/5 X7/9-X-88/-6XXX81 -> Run.
 * @author Matthew Kelly */
public final class BowlingScoreCard {

	private static final char	MISS		= '-';
	private static final char	SPARE		= '/';
	private static final char	STRIKE		= 'X';
	private static final int	TEN_FRAMES	= 10;
	private static final int	TEN_PINS	= 10;

	private int					position;
	private int					score;
	private String				scoreInput;

	/** Can be run as a standalone program to score 1 or more games.
	 * @param args 1 or more standalone scores */
	public static void main(String[] args) {
		for (String arg : args) {
			BowlingScoreCard card = new BowlingScoreCard(arg);
			System.out.println(String.format("Game %s was scored as %s points", arg, card.getGameScore()));
		}
	}

	/** Receives the valid sequence of rolls for Ten-Frames of bowling. Each roll/throw of the ball can be represented as
	 * a 'X' for a Strike, '/' for a Spare, '-' for a Miss, and 1-9 for a few pins. Examples for an entire frame
	 * includes 'X', '3/', '5-', or '34'.
	 * @param scoreInput Valid sequence of all Rolls */
	public BowlingScoreCard(String scoreInput) {
		score = 0;
		this.scoreInput = scoreInput;
	}

	public int getGameScore() {
		if (score == 0 || position == 0) {
			IntStream.rangeClosed(1, TEN_FRAMES).forEach(i -> score += getFrameScore());
		}
		return score;
	}

	private int getFrameScore() {
		if (position == scoreInput.length()) {
			return 0;
		}
		char firstRoll = scoreInput.charAt(position++);
		if (firstRoll == STRIKE) {
			return scoreStrike(scoreInput, position);
		}
		else {
			char secondRoll = scoreInput.charAt(position++);
			if (secondRoll == SPARE) {
				return scoreSpare(scoreInput, position);
			}
			else {
				return charScore(firstRoll) + charScore(secondRoll);
			}
		}
	}

	public static final int scoreSpare(String scoreInput, int position) {
		return TEN_PINS + charScore(scoreInput.charAt(position));
	}

	public static final int scoreStrike(String scoreInput, int position) {
		char secondRoll = scoreInput.charAt(position + 1);
		if (secondRoll == SPARE) {
			return 2 * TEN_PINS;
		}
		return scoreSpare(scoreInput, position) + charScore(secondRoll);
	}

	private static final int charScore(char symbol) {
		switch (symbol) {
			case STRIKE:
				return TEN_PINS;
			case SPARE:
				return TEN_PINS;
			case MISS:
				return 0;
			default:
				return Character.getNumericValue(symbol);
		}
	}

}
