(comment
  A USER consists of:
 
  A username (32 varchar)
  A password (text)
  An email address (text)

  A USER has (i.e. username is the foreign key of):

  One or more LOCATIONs
  Zero or more REQUESTs for food
  Zero or more PROMISEs to fulfill an order
  Zero or more FULFILLMENTs 
 
  A USER is uniquely identified by his username
  --

  A SESSION consists of:
  a USERNAME (fk)
  a unique identifier
  a expiration time (date)
  --

  
  A  REQUEST consists of:
  
  A username (fk USER.username)
  A creation time (time)
  An expiration  time (time)
  A price (money)
  A note (text)

  A REQUEST has (i.e. is the fk of):
  Zero or more REQUEST-AMENDMENTS
  Zero or one FULFILLMENTs
  
  A REQUEST is uniquely identified by requester & creation-time 
  --
  
  A PROMISE consists of:

  A REQUEST (fk REQUEST.requester & REQUEST.creation-time)
  A promiser (fk USER.username)
  A creation time (time)

  A PROMISE has (i.e. is the fk of):
  Zero or more PROMISE-AMENDMENTs
  
  A PROMISE is uniquely identified by promiser & creation-time
  --

  
  A PROMISE-AMENDMENT consists of:

  A PROMISE (fk PROMISE.promiser & PROMISE.creation-time)
  An amender (fk USER.username)
  A note (text)
  A price (money)
  An acceptance (fk USER.username or NULL)
  --

  
  A REQUEST-AMENDMENT consists of:

  A REQUEST (fk REQUEST.requester & REQUEST.creation-time)
  An amender (fk USER.username)
  A note (text)
  A price (money)
  An acceptance (fk USER.username or NULL)
  --
  A FULFILLMENT consists of:

  
  A LOCATION consists of:

  a username (fk USERS.username)
  a zipcode (varchar 16, optional)
  a latitude (float)
  a longitude (float)
  
  )