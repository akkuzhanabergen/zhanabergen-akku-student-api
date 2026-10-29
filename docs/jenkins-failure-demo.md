# Jenkins controlled failure and recovery (test branch only)

Do not break `main`. Use a separate branch named `feature/zhanabergen-akku-pipeline-demo`.

1. Create the branch from main: `git switch -c feature/zhanabergen-akku-pipeline-demo`.
2. In `StudentApiIntegrationTest.java`, temporarily change the expected student name in the `infoContainsStudentName()` assertion to `Wrong Student`.
3. Commit: `git add src && git commit -m "Zhanabergen Akku: demonstrate failed CI test"`.
4. Push the branch: `git push -u origin feature/zhanabergen-akku-pipeline-demo`.
5. In Jenkins, create a separate Pipeline job from this branch (or temporarily change the SCM branch in a demo job). Trigger a build: **Test** fails and **Docker Build** is skipped. Capture genuine console output.
6. Restore the assertion to `Zhanabergen Akku`, commit `Zhanabergen Akku: fix intentionally failing test` and push.
7. Run the same Jenkins job again and capture the successful result.
8. Leave the main Pipeline job targeting the `main` branch.

If a new commit on GitHub does not cause a build, check Jenkins polling settings or webhook connectivity, Jenkins Git credentials, and selected branch.
