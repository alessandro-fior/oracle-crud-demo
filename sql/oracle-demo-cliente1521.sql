SELECT column_name,
       data_default,
       identity_column
FROM user_tab_columns
WHERE table_name = 'CLIENTE'
  AND column_name = 'ID';
  
  
  SELECT MAX(ID) AS MAX_ID FROM CLIENTE;
  
  SELECT sequence_name,
       last_number
FROM user_sequences
WHERE sequence_name = 'ISEQ$$_75856';