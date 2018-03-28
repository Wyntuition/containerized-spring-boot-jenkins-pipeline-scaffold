multibranchPipelineJob('vis-case-processing-etl-nonprod-test') {
    branchSources {
        git {
            remote('git@git.uscis.dhs.gov:USCIS/vis-case-processing-etl')
            excludes('master production-*')
        }
    }
    configure { project ->
        project / 'sources' / 'data' / 'jenkins.branch.BranchSource'/ strategy(class: 'jenkins.branch.DefaultBranchPropertyStrategy') {
            properties(class: 'java.util.Arrays$ArrayList') {
                a(class: 'jenkins.branch.BranchProperty-array'){
                    'jenkins.branch.NoTriggerBranchProperty'()
                }
            }
        }
    }
    triggers {
        periodic(1)
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(5)
        }
    }
}
