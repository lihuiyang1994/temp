package demo.docker.temp.config.checker;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * lihui
 * PreUserAuthenticationChecksList
 *
 * @description
 */
public class PreUserAuthenticationChecksList implements UserDetailsChecker {

    private Set<UserDetailsCheckerAdapter> checkers = new HashSet<>();

    @Override
    public void check(UserDetails userDetails) {
        for (UserDetailsCheckerAdapter checker : checkers) {
            Class<? extends UserDetails> support = checker.support();
            if (support.equals(userDetails.getClass())){
                checker.check(userDetails);
            }
        }
    }

    public void addChecker(UserDetailsCheckerAdapter checker){
        checkers.add(checker);
    }

    public void removeChecker(UserDetailsCheckerAdapter checker){
        checkers.remove(checker);
    }
    public void addAllCheckers(Collection<UserDetailsCheckerAdapter> checkerCol){
        checkers.addAll(checkerCol);
    }

}
