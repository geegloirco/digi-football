export class LoginModel {
  username: string;
  isLoggedIn: boolean;
  isAdmin: boolean;
  isGuest: boolean;

  constructor(username: string,
              isAdmin: boolean,
              isGuest: boolean,
              isLoggedIn: boolean) {
    this.username = username;
    this.isAdmin = isAdmin;
    this.isGuest = isGuest;
    this.isLoggedIn = isLoggedIn;
  }
}
