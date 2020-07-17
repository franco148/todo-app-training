export interface TokenBody {
  sub: string;
  authorities: string[];
  AuthUserId: number;
  iat: number;
  exp: number;
}
