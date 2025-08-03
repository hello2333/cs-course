#
# @lc app=leetcode id=1 lang=python3
#
# [1] Two Sum
#

# @lc code=start
class Solution:
    def twoSum(self, nums: list[int], target: int) -> list[int]:
        indices = []
        if len(indices) < 2:
            return

        self.twoSumHelperV1(nums, target, 0, indices)
        return indices

    def twoSumHelperV1(self, nums: list[int], target: int, start: int, indices: list[int]):
        for i in range(start, len(nums)):
            # if nums[i] > target:
            #     return False
            if nums[i] == target and len(indices) == 1:
                indices.append(i)
                return True
            target = target - nums[i]
            indices.append(i)
            for j in range(i + 1, len(nums)):
                if self.twoSumHelperV1(nums, target, j, indices):
                    return
            target = target + nums[i]
            indices.pop()
    
    def twoSumHelperWithMem(self, nums: list[int], target: int, start: int, indices: list[int], dp: list[list[int]]):
        for i in range(start, len(nums)):
            if nums[i] == target and len(indices) == 1:
                indices.append(i)
                dp[start][target] = i
                return True
            if len(indices) >= 2:
                dp[start][target] = -1
                return False
            target = target - nums[i]
            indices.append(i)
            for j in range(i + 1, len(nums)):
                if dp[j][target] == -1:
                    break
                if dp[j][target] >= 0:
                    indices.append(dp[j][target])
                    return
                if self.twoSumHelperV1(nums, target, j, indices):
                    return
            target = target + nums[i]
            indices.pop()
            dp[start][target] = -1
            
        
# @lc code=end

