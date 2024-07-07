#
# @lc app=leetcode id=744 lang=python3
#
# [744] Find Smallest Letter Greater Than Target
#

# @lc code=start
from typing import List


class Solution:
    def nextGreatestLetter(self, letters: List[str], target: str) -> str:
        left = 0
        right = len(letters) - 1
        rightBounder = len(letters)
        while left <= right and right < rightBounder and left >= 0:
            middle = int((right - left) / 2) + left
            # print("middle: ", middle, letters[middle], "left: ", left, "right: ", right)
            if letters[middle] <= target:
                # print("middle is smaller, go to right")
                # if not has right part: break
                if  middle + 1 >= rightBounder:
                    break
                # else continue with right part
                left = middle + 1
            if letters[middle] > target:
                # print("middle is smaller, go to left")
                # if not has left part: return middle
                if middle - 1 < 0:
                    return letters[middle]
                # if max of left part smaller than target: return middle
                if letters[middle - 1] <= target:
                    return letters[middle]
                # continue with left part
                right = middle - 1
        return letters[0]
        
# @lc code=end

test = Solution()
print(test.nextGreatestLetter(["c","f","j"], "g"))

