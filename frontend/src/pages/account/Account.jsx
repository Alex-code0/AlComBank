import React, { useEffect, useState } from 'react'
import './account.css'

const Account = () => {
  const [account, setAccount] = useState(null)
  const [recipient, setTransferCardNumber] = useState("")
  const [transferAmount, setTransferAmount] = useState("")

  const handleTransferCardNumberChange = (e) => setTransferCardNumber(e.target.value)
  const handleTransferAmountChange = (e) => setTransferAmount(e.target.value)

  useEffect(() => {
    const accountData = JSON.parse(localStorage.getItem('accountData'))
    setAccount(accountData)
  }, []);

  const handleTransfer = async (e) => {
    e.preventDefault()

    if (transferAmount <= 0) {
      alert("Amount should be greater than 0")
      return;
    }

    const sender = account?.cardNumber
    const transferData = { sender, recipient, transferAmount }

    try {
      const response = await fetch("http://localhost:8080/api/transfer", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(transferData),
      })

      const responseData = await response.json();
      if (response.ok) {
        alert("Transfer successful")
      } else {
        alert("Transfer failed")
      }
    } catch (error) {
      console.error("Error: ", error)
    }
  };

  return (
    <div className="account-container">
      <div className="account-details">
        <h2>Account Details</h2>
        {account ? (
          <>
            <p><strong>Balance:</strong> {account.balance}</p>
            <p><strong>Card Number:</strong> {account.cardNumber || 'N/A'}</p>
            <p><strong>Expire Date:</strong> {account.expireDate || 'N/A'}</p>
            <input
              type="text"
              placeholder="Card number"
              value={recipient}
              onChange={handleTransferCardNumberChange}
            />
            <input
              type="number"
              placeholder="Amount to transfer"
              value={transferAmount}
              onChange={handleTransferAmountChange}
            />
            <button onClick={handleTransfer}>
              Transfer
            </button>
          </>
        ) : (
          <p>Loading account details...</p>
        )}
      </div>
    </div>
  );
};

export default Account