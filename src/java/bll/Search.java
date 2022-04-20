package bll;

import be.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class Search {

    public List<UserInfo> search(List<UserInfo> users, String query)
    {
        List<UserInfo> result = new ArrayList<>();

        for (UserInfo user: users)
        {
            if (compareForeName(user, query))
            {
                result.add(user);
                System.out.println(user.getName());
            }
        }
        return users;
    }

    private Boolean compareForeName(UserInfo user, String query)
    {
        String[] name = user.getName().split(" ");
        return name[0].toLowerCase().contains(query);
    }
}
