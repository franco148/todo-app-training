import { UserModel } from '../models/user.model';

export interface AuthResponse {
  message: string;
  token: string;
  user: UserModel;
}
