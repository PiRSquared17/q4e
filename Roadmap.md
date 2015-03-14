# Introduction #

This page describes the planned stages in q4e development.

Evolution of the roadmap should be discussed in the developers user list and will be driven mostly by the community.

# Details #

## Phase 1: Tooling for existing maven2 users ##
This is the current stage of development.

Focus is on making existing maven projects usable with eclipse.

  * Import existing projects into eclipse
  * Reproduce maven builds inside eclipse
  * Create maven projects using archetypes

## Phase 2: Tooling for eclipse users ##
After the basic maven functionality has been achieved, we'll introduce features targeted specifically to the eclipse environment.

We expect to reach this phase early in 2008.

During phase 2, added features could lead to non-reproducible builds.

  * Full embedding: no need to use m2 from the command line (project creation, importing projects/multiprojects from SCM...)
  * Definition of maven properties in eclipse (risk of non reproducible builds).
  * Use eclipse proxy settings instead of maven's (risk of on reproducible builds).
  * Open architecture for integration with 3rd parties (WTP / Spring IDE / AppFuse...)

## Phase 3: Not defined yet ##
If you have ideas for phase 3, please propose them.