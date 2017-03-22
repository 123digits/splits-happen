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
	private int					position	= 0;
	private int					score		= 0;

	/** Can be run as a standalone program to score 1 or more games.
	 * @param args 1 or more Valid Sequences of all rolls in a Ten-Frame game */
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
		IntStream.rangeClosed(1, TEN_FRAMES).forEach(i -> score += getFrameScore(scoreInput));
	}

	/** Returns the final score for a valid sequence of all rolls for a Ten-Frame game.
	 * @return Final score */
	public int getGameScore() {
		return score;
	}

	/** Scores a single frame in a valid sequence of all rolls of a Ten-Frame game.
	 * @param scoreInput Valid sequence of all rolls of a Ten-Frame game
	 * @return int value for an entire frame of a Ten-Frame game */
	private int getFrameScore(String scoreInput) {
		if (scoreInput.charAt(position) == STRIKE)
			return TEN_PINS + getCharScore(scoreInput, ++position) + getCharScore(scoreInput, position + 1);
		else if (scoreInput.charAt(position + 1) == SPARE)
			return TEN_PINS + getCharScore(scoreInput, position += 2);
		else
			return getCharScore(scoreInput, position++) + getCharScore(scoreInput, position++);
	}

	/** Returns the exact number of pins that were knocked down in a single roll. 'X' for Strike -> 10 pins. '/' for
	 * Spare -> 10 pins minus the previous roll. '-' for Miss -> 0 pins. '3' -> 3 pins.
	 * @param inputScore Valid sequence of all rolls of a Ten-Frame game
	 * @param position Position for a single roll in the valid sequence of all rolls of a Ten-Frame game
	 * @return int value for a single roll */
	private static int getCharScore(String inputScore, int position) {
		switch (inputScore.charAt(position)) {
			case STRIKE:
				return TEN_PINS;
			case SPARE:
				return TEN_PINS - Character.getNumericValue(inputScore.charAt(position - 1));
			case MISS:
				return 0;
			default:
				return Character.getNumericValue(inputScore.charAt(position));
		}
	}

}
