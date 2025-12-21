package com.pokerfans.excelsums.service;

import com.pokerfans.excelsums.dto.CombinationResult;

import java.util.*;

public class CombinationSumService {

    private static final int MAX_RESULT = 5;
    private static final double EPS = 1e-6; // 浮点精度容差

    public Map<Double, List<CombinationResult>> calculate(
            List<Double> numbers,
            List<Double> targets) {

        // 倒序排序，大数字优先
        numbers.sort(Comparator.reverseOrder());

        Map<Double, List<CombinationResult>> result = new LinkedHashMap<>();

        for (Double target : targets) {
            List<List<Double>> raw = new ArrayList<>();
            backtrack(numbers, target, 0, 0.0, new ArrayList<>(), raw);

            List<CombinationResult> wrapped = raw.stream()
                    .map(CombinationResult::new)
                    .toList();

            result.put(target, wrapped);
        }

        return result;
    }

    private void backtrack(List<Double> nums,
                           double target,
                           int start,
                           double sum,
                           List<Double> path,
                           List<List<Double>> result) {

        if (result.size() >= MAX_RESULT) return;

        if (Math.abs(sum - target) < EPS) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < nums.size(); i++) {

            // 同层去重
            if (i > start && Math.abs(nums.get(i) - nums.get(i - 1)) < EPS) continue;

            double val = nums.get(i);

            // 剪枝
            if (sum + val - target > EPS) continue;

            path.add(val);
            backtrack(nums, target, i + 1, sum + val, path, result);
            path.remove(path.size() - 1);

            if (result.size() >= MAX_RESULT) return;
        }
    }
}
