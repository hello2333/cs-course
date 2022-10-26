"""Typing test implementation"""

from curses import nonl
from locale import currency
from nis import match
import sys
import types
from utils import *
from ucb import main, interact, trace
from datetime import datetime


###########
# Phase 1 #
###########


def choose(paragraphs, select, k):
    """Return the Kth paragraph from PARAGRAPHS for which SELECT called on the
    paragraph returns true. If there are fewer than K such paragraphs, return
    the empty string.
    """
    # BEGIN PROBLEM 1
    "*** YOUR CODE HERE ***"
    count = -1
    for p in paragraphs:
        if select(p):
            count += 1
            if count == k:
                return p
    return ""
    # END PROBLEM 1


def about(topic):
    """Return a select function that returns whether a paragraph contains one
    of the words in TOPIC.

    >>> about_dogs = about(['dog', 'dogs', 'pup', 'puppy'])
    >>> choose(['Cute Dog!', 'That is a cat.', 'Nice pup!'], about_dogs, 0)
    'Cute Dog!'
    >>> choose(['Cute Dog!', 'That is a cat.', 'Nice pup.'], about_dogs, 1)
    'Nice pup.'
    """
    assert all([lower(x) == x for x in topic]), 'topics should be lowercase.'
    # BEGIN PROBLEM 2
    "*** YOUR CODE HERE ***"
    def p_about_topic(paragraph):
        simplified_p_list = split(lower(remove_punctuation(paragraph)))
        for p_item in simplified_p_list:
            for item in topic:
                if p_item == item:
                    return True
        return False
    return p_about_topic
    # END PROBLEM 2

# 1、没有*100
# 2、没有处理len(typed_words) == 0的边界情况
# 3、题目好像变了……和他给的unlock不符合
# >>> accuracy("a b c d", " a d ")
# ? 25.0
# -- OK! --
def accuracy(typed, reference):
    """Return the accuracy (percentage of words typed correctly) of TYPED
    when compared to the prefix of REFERENCE that was typed.

    >>> accuracy('Cute Dog!', 'Cute Dog.')
    50.0
    >>> accuracy('A Cute Dog!', 'Cute Dog.')
    0.0
    >>> accuracy('cute Dog.', 'Cute Dog.')
    50.0
    >>> accuracy('Cute Dog. I say!', 'Cute Dog.')
    50.0
    >>> accuracy('Cute', 'Cute Dog.')
    100.0
    >>> accuracy('', 'Cute Dog.')
    0.0
    """
    typed_words = split(typed)
    reference_words = split(reference)
    if len(typed_words) == 0:
        return 0.0
    # BEGIN PROBLEM 3
    "*** YOUR CODE HERE ***"
    compare_size = min(len(typed_words), len(reference_words))
    match_count = 0
    #print(len(typed_words), len(reference_words), compare_size)
    for i in range(compare_size):
        if typed_words[i] == reference_words[i]:
            match_count += 1
            #print(typed_words[i], reference_words[i], match_count)
        elif match_count == 0:
            #print(typed_words[i], reference_words[i], "continue")
            continue
        else:
            #print(typed_words[i], reference_words[i], "break")
            continue
    
    return match_count / len(typed_words) * 100
    # END PROBLEM 3


def wpm(typed, elapsed):
    """Return the words-per-minute (WPM) of the TYPED string."""
    assert elapsed > 0, 'Elapsed time must be positive'
    # BEGIN PROBLEM 4
    "*** YOUR CODE HERE ***"
    return len(typed) * 60 / elapsed / 5
    # END PROBLEM 4

# 1. user_word == valid_item 写成了 user_word == valid_words
# 2. curr_diff <= limit 写成了 curr_diff < limit，题意没有读对
def autocorrect(user_word, valid_words, diff_function, limit):
    """Returns the element of VALID_WORDS that has the smallest difference
    from USER_WORD. Instead returns USER_WORD if that difference is greater
    than LIMIT.
    """
    # BEGIN PROBLEM 5
    "*** YOUR CODE HERE ***"
    min_diff = sys.maxsize
    similar_word = ""
    for valid_item in valid_words:
        if user_word == valid_item:
            return user_word
        curr_diff = diff_function(user_word, valid_item, limit)
        if curr_diff < min_diff and curr_diff <= limit:
            min_diff = curr_diff
            similar_word = valid_item
    if similar_word != "":
        return similar_word
    return user_word
    # END PROBLEM 5

# 1. 用错了变量，curr_limit写成了curr
# 2. 没有考虑边界情况：limit == 0的时候
def shifty_shifts(start, goal, limit):
    """A diff function for autocorrect that determines how many letters
    in START need to be substituted to create GOAL, then adds the difference in
    their lengths.
    """
    # BEGIN PROBLEM 6
    curr_limit = 0
    def shifty_shifts_helper(start, goal, limit):
        nonlocal curr_limit
        if len(start) == len(goal) and len(start) == 0:
            return
        if len(start) == 0:
            curr_limit += len(goal)
            return
        if len(goal) == 0:
            curr_limit += len(start)
            return
        # if curr_limit >= limit and curr_limit > 0:
        #     if curr_limit > limit:
        #         return
        #     curr_limit += 1
        #     return
        if start[0] != goal[0] and curr_limit == limit:
            curr_limit += 1
            return
        elif start[0] != goal[0] and curr_limit < limit:
            curr_limit += 1
            shifty_shifts_helper(start[1:], goal[1:], limit)
        elif start[0] != goal[0] and curr_limit > limit:
            print("scenes exist")
            return
        else:
            shifty_shifts_helper(start[1:], goal[1:], limit)
    shifty_shifts_helper(start, goal, limit)
    return curr_limit
    # END PROBLEM 6


def meowstake_matches(start, goal, limit):
    """A diff function that computes the edit distance from START to GOAL."""
    #assert False, 'Remove this line'
    def meowstake_matches_helper(start, goal, curr_limit, limit):
        if start == goal: # Fill in the condition
            # BEGIN
            "*** YOUR CODE HERE ***"
            return curr_limit
            # END
        elif len(start) == 0: # Feel free to remove or add additional cases
            # BEGIN
            "*** YOUR CODE HERE ***"
            return len(goal) + curr_limit
            # END
        elif len(goal) == 0:
            return len(start) + curr_limit
        elif start[0] == goal[0]:
            return meowstake_matches_helper(start[1:], goal[1:], curr_limit, limit)
        elif curr_limit == limit:
            return curr_limit + 1
        else:
            add_diff = meowstake_matches_helper(start, goal[1:], curr_limit + 1, limit)
            remove_diff = meowstake_matches_helper(start[1:], goal, curr_limit + 1, limit)
            substitute_diff = meowstake_matches_helper(start[1:], goal[1:], curr_limit + 1, limit) 
            # BEGIN
            "*** YOUR CODE HERE ***"
            return min(add_diff, remove_diff, substitute_diff)
            # END
    return meowstake_matches_helper(start, goal, 0, limit)


def final_diff(start, goal, limit):
    """A diff function. If you implement this function, it will be used."""
    assert False, 'Remove this line to use your final_diff function'


###########
# Phase 3 #
###########


def report_progress(typed, prompt, id, send):
    """Send a report of your id and progress so far to the multiplayer server."""
    # BEGIN PROBLEM 8
    "*** YOUR CODE HERE ***"
    same_size = 0
    for i in range(len(typed)):
        if typed[i] != prompt[i]:
            break
        same_size += 1
    ratio = same_size / len(prompt)
    dict = {"id": id, "progress": ratio}
    send(dict)
    return ratio
    # END PROBLEM 8


def fastest_words_report(times_per_player, words):
    """Return a text description of the fastest words typed by each player."""
    game = time_per_word(times_per_player, words)
    fastest = fastest_words(game)
    report = ''
    for i in range(len(fastest)):
        words = ','.join(fastest[i])
        report += 'Player {} typed these fastest: {}\n'.format(i + 1, words)
    return report


def time_per_word(times_per_player, words):
    """Given timing data, return a game data abstraction, which contains a list
    of words and the amount of time each player took to type each word.

    Arguments:
        times_per_player: A list of lists of timestamps including the time
                          the player started typing, followed by the time
                          the player finished typing each word.
        words: a list of words, in the order they are typed.
    """
    # BEGIN PROBLEM 9
    "*** YOUR CODE HERE ***"
    differences_all_player = []
    for times_one_player in times_per_player:
        differences_one_player = []
        for i in range(len(times_one_player) - 1):
            differences_one_turn = times_one_player[i + 1] - times_one_player[i]
            differences_one_player.append(differences_one_turn)
        differences_all_player.append(differences_one_player)
    return game(words, differences_all_player)
    # END PROBLEM 9


def fastest_words(game):
    """Return a list of lists of which words each player typed fastest.

    Arguments:
        game: a game data abstraction as returned by time_per_word.
    Returns:
        a list of lists containing which words each player typed fastest
    """
    players = range(len(all_times(game)))  # An index for each player
    words = range(len(all_words(game)))    # An index for each word
    # BEGIN PROBLEM 10
    "*** YOUR CODE HERE ***"
    player_fast_words = [[] for i in players]
    for i in words:
        min_time = sys.maxsize
        min_player = -1
        for player in players:
            if time(game, player, i) < min_time:
                min_player = player
                min_time = time(game, player, i)
        player_fast_words[min_player].append(word_at(game, i))
    return player_fast_words
    # END PROBLEM 10


def game(words, times):
    """A data abstraction containing all words typed and their times."""
    assert all([type(w) == str for w in words]), 'words should be a list of strings'
    assert all([type(t) == list for t in times]), 'times should be a list of lists'
    assert all([isinstance(i, (int, float)) for t in times for i in t]), 'times lists should contain numbers'
    assert all([len(t) == len(words) for t in times]), 'There should be one word per time.'
    return [words, times]


def word_at(game, word_index):
    """A selector function that gets the word with index word_index"""
    assert 0 <= word_index < len(game[0]), "word_index out of range of words"
    return game[0][word_index]


def all_words(game):
    """A selector function for all the words in the game"""
    return game[0]


def all_times(game):
    """A selector function for all typing times for all players"""
    return game[1]


def time(game, player_num, word_index):
    """A selector function for the time it took player_num to type the word at word_index"""
    assert word_index < len(game[0]), "word_index out of range of words"
    assert player_num < len(game[1]), "player_num out of range of players"
    return game[1][player_num][word_index]


def game_string(game):
    """A helper function that takes in a game object and returns a string representation of it"""
    return "game(%s, %s)" % (game[0], game[1])

enable_multiplayer = False  # Change to True when you

##########################
# Extra Credit #
##########################

key_distance = get_key_distances()
def key_distance_diff(start, goal, limit):
    """ A diff function that takes into account the distances between keys when
    computing the difference score."""

    start = start.lower() #converts the string to lowercase
    goal = goal.lower() #converts the string to lowercase

    # BEGIN PROBLEM EC1
    "*** YOUR CODE HERE ***"
    change_count = 0
    differences = 0
    for i in range(len(start)):
        if start[i] == goal[i]:
            continue
        if change_count > limit:
            return float('inf')
        differences += key_distance[start[i], goal[i]]
        change_count += 1
    return differences
    # END PROBLEM EC1

def memo(f):
    """A memoization function as seen in John Denero's lecture on Growth"""

    cache = {}
    def memoized(*args):
        if args not in cache:
            cache[args] = f(*args)
        return cache[args]
    return memoized

key_distance_diff = count(key_distance_diff)


def faster_autocorrect(user_word, valid_words, diff_function, limit):
    """A memoized version of the autocorrect function implemented above."""

    # BEGIN PROBLEM EC2
    "*** YOUR CODE HERE ***"
    # END PROBLEM EC2


##########################
# Command Line Interface #
##########################


def run_typing_test(topics):
    """Measure typing speed and accuracy on the command line."""
    paragraphs = lines_from_file('data/sample_paragraphs.txt')
    select = lambda p: True
    if topics:
        select = about(topics)
    i = 0
    while True:
        reference = choose(paragraphs, select, i)
        if not reference:
            print('No more paragraphs about', topics, 'are available.')
            return
        print('Type the following paragraph and then press enter/return.')
        print('If you only type part of it, you will be scored only on that part.\n')
        print(reference)
        print()

        start = datetime.now()
        typed = input()
        if not typed:
            print('Goodbye.')
            return
        print()

        elapsed = (datetime.now() - start).total_seconds()
        print("Nice work!")
        print('Words per minute:', wpm(typed, elapsed))
        print('Accuracy:        ', accuracy(typed, reference))

        print('\nPress enter/return for the next paragraph or type q to quit.')
        if input().strip() == 'q':
            return
        i += 1


@main
def run(*args):
    """Read in the command-line argument and calls corresponding functions."""
    import argparse
    parser = argparse.ArgumentParser(description="Typing Test")
    parser.add_argument('topic', help="Topic word", nargs='*')
    parser.add_argument('-t', help="Run typing test", action='store_true')

    args = parser.parse_args()
    if args.t:
        run_typing_test(args.topic)