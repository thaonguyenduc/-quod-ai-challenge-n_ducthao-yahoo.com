package com.company.model;

import com.google.common.base.Objects;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

/**
 * Represents export record.
 */
public class HealthRepo {
    @CsvBindByName(column = "Organization")
    @CsvBindByPosition(position = 0)
    private String org;
    @CsvBindByName(column = "Repo")
    @CsvBindByPosition(position = 1)
    private String repoName;
    @CsvBindByName(column = "Health Score")
    @CsvBindByPosition(position = 2)
    private Double healthScore;
    @CsvBindByName(column = "Average Commit Per Day")
    @CsvBindByPosition(position = 3)
    private Double averageCommitPerDay;
    @CsvBindByName(column = "Average Issue Remained Open")
    @CsvBindByPosition(position = 4)
    private Double averageIssueRemainedOpen;
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "Average Time Merged")
    private Double averageTimePrsMerged;
    @CsvBindByName(column = "Average Commit Per Developer")
    @CsvBindByPosition(position = 6)
    private Double ratioCommitPerDeveloper;

    public static HealthRepo create(double averageCommitPerDay,
                                    double ratioCommitPerDeveloper,
                                    double averageIssueRemainedOpen,
                                    double averagePullRequestMerged,
                                    double healthScore, String[] orgRepo) {
        return new HealthRepo()
                .setOrg(orgRepo[0])
                .setRepoName(orgRepo.length == 1 ? orgRepo[0] : orgRepo[1])
                .setAverageCommitPerDay(averageCommitPerDay)
                .setAverageIssueRemainedOpen(averageIssueRemainedOpen)
                .setAverageTimePrsMerged(averagePullRequestMerged)
                .setRatioCommitPerDeveloper(ratioCommitPerDeveloper)
                .setHealthScore(healthScore);
    }

    public String getRepoName() {
        return repoName;
    }

    public HealthRepo setRepoName(String repoName) {
        this.repoName = repoName;
        return this;
    }

    public String getOrg() {
        return org;
    }

    public HealthRepo setOrg(String org) {
        this.org = org;
        return this;
    }

    public Double getAverageCommitPerDay() {
        return averageCommitPerDay;
    }

    public HealthRepo setAverageCommitPerDay(Double averageCommitPerDay) {
        this.averageCommitPerDay = averageCommitPerDay;
        return this;
    }

    public Double getAverageIssueRemainedOpen() {
        return averageIssueRemainedOpen;
    }

    public HealthRepo setAverageIssueRemainedOpen(Double averageIssueRemainedOpen) {
        this.averageIssueRemainedOpen = averageIssueRemainedOpen;
        return this;
    }

    public Double getAverageTimePrsMerged() {
        return averageTimePrsMerged;
    }

    public HealthRepo setAverageTimePrsMerged(Double averageTimePrsMerged) {
        this.averageTimePrsMerged = averageTimePrsMerged;
        return this;
    }

    public Double getRatioCommitPerDeveloper() {
        return ratioCommitPerDeveloper;
    }

    public HealthRepo setRatioCommitPerDeveloper(Double ratioCommitPerDeveloper) {
        this.ratioCommitPerDeveloper = ratioCommitPerDeveloper;
        return this;
    }

    public Double getHealthScore() {
        return healthScore;
    }

    public HealthRepo setHealthScore(Double healthScore) {
        this.healthScore = healthScore;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthRepo healthRepo = (HealthRepo) o;
        return Objects.equal(org, healthRepo.org) &&
                Objects.equal(repoName, healthRepo.repoName) &&
                Objects.equal(healthScore, healthRepo.healthScore) &&
                Objects.equal(averageCommitPerDay, healthRepo.averageCommitPerDay) &&
                Objects.equal(averageIssueRemainedOpen, healthRepo.averageIssueRemainedOpen) &&
                Objects.equal(averageTimePrsMerged, healthRepo.averageTimePrsMerged) &&
                Objects.equal(ratioCommitPerDeveloper, healthRepo.ratioCommitPerDeveloper);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(org, repoName, healthScore, averageCommitPerDay, averageIssueRemainedOpen, averageTimePrsMerged, ratioCommitPerDeveloper);
    }
}
