export const NOT_FOUND_NAME = 'NotFoundError';

class NotFoundError extends Error {
  name = NOT_FOUND_NAME;
}

export default NotFoundError;
